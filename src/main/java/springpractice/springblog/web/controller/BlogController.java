package springpractice.springblog.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Category;
import springpractice.springblog.domain.Member;
import springpractice.springblog.domain.Post;
import springpractice.springblog.service.CategoryService;
import springpractice.springblog.service.MemberService;
import springpractice.springblog.service.PostService;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {

    private final MemberService memberService;
    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/{memberId}")
    public String showMyBlog(@PathVariable String memberId, Model model) {
        Member member = memberService.findOne(memberId).get();
        Blog blog = member.getBlog();
        List<Category> categories = blog.getCategories();
        List<Post> posts = postService.findPageByBlog(blog, 0, 5);

        model.addAttribute("categories", categories);
        blogSetting(model, member, posts, "카테고리");
        return "blogs/myBlog";
    }

    @GetMapping("/{memberId}/category/{categoryName}")
    public String showMyBlogByCategory(@PathVariable String memberId, @PathVariable String categoryName,
                                       Model model) {
        Member member = memberService.findOne(memberId).get();
        Blog blog = member.getBlog();
        Optional<Category> category = categoryService.findByBlogAndName(blog, categoryName);
        List<Post> posts = category.get().getPosts();

        blogSetting(model, member, posts, categoryName);
        return "blogs/myBlog";
    }

    private void blogSetting(Model model, Member member, List<Post> posts, String categoryName) {
        model.addAttribute("loginMember", member);
        model.addAttribute("blogTitle", member.getBlog().getTitle());
        model.addAttribute("posts", posts);
        model.addAttribute("categoryName", categoryName);
    }
}
