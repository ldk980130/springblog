package springpractice.springblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springpractice.springblog.domain.Post;
import springpractice.springblog.service.PostService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String anonymousHome(Model model) {
        Page<Post> posts = postService.findPageFromAllPosts(0, 5);
        model.addAttribute("posts", posts);
        return "anonymousHome";
    }
}
