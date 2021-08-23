package springpractice.springblog.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import springpractice.springblog.domain.Member;
import springpractice.springblog.domain.Post;
import springpractice.springblog.service.PostService;
import springpractice.springblog.web.SessionConst;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public String showPost(@PathVariable Long postId, Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Post post = postService.findOne(postId);
        String writer = post.getCategory().getBlog().getMember().getName();

        model.addAttribute("post", post);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("writer", writer);

        return "posts/showOnePost";
    }
}
