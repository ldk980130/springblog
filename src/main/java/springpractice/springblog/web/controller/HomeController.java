package springpractice.springblog.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import springpractice.springblog.domain.Member;
import springpractice.springblog.domain.Post;
import springpractice.springblog.service.PostService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String homeController(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model) {

        Page<Post> posts = postService.findPageFromAllPosts(0, 5);
        model.addAttribute("posts", posts);

        if (loginMember == null) {
            return "anonymousHome";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
