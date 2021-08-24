package springpractice.springblog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Category;
import springpractice.springblog.domain.Comment;
import springpractice.springblog.domain.Member;
import springpractice.springblog.domain.Post;
import springpractice.springblog.service.CategoryService;
import springpractice.springblog.service.CommentService;
import springpractice.springblog.service.MemberService;
import springpractice.springblog.service.PostService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final CommentService commentService;

    @PostConstruct
    public void init() {
        Member memberTester = memberService.join("test", "test!", "테스터");
        Member memberLee = memberService.join("ldk3", "0130", "lee");

        Category testerBackCate = categoryService.resister(memberTester.getBlog(), "백엔드");
        Category testerFrontCate = categoryService.resister(memberTester.getBlog(), "프론트엔드");
        Category leeBackCate = categoryService.resister(memberLee.getBlog(), "백엔드");
        Category leeFrontCate = categoryService.resister(memberLee.getBlog(), "프론트엔드");

        Post post1 = postService.resister(testerBackCate, "스프링", "어렵다");
        Post post2 = postService.resister(testerFrontCate, "자바스크립트", "어렵다");
        Post post3 = postService.resister(leeBackCate, "자바", "어렵다");
        Post post4 = postService.resister(leeFrontCate, "리액트", "어렵다");
        Post post5 = postService.resister(leeFrontCate, "css", "어렵다");
        Post post6 = postService.resister(leeFrontCate, "html", "어렵다");


        commentService.resisterSuper(memberTester, post1, "댓글1");
        Comment comment2 = commentService.resisterSuper(memberLee, post1, "댓글2");
        commentService.resisterSub(memberTester, comment2, "대댓글1");
        commentService.resisterSub(memberTester, comment2, "대댓글2");

        commentService.resisterSuper(memberTester, post2, "댓글3");
        Comment comment4 = commentService.resisterSuper(memberLee, post2, "댓글4");
        commentService.resisterSub(memberTester, comment4, "대댓글1");
        commentService.resisterSub(memberTester, comment4, "대댓글2");
        commentService.resisterSub(memberTester, comment4, "대댓글3");

        commentService.resisterSuper(memberTester, post3, "댓글5");
        commentService.resisterSuper(memberLee, post3, "댓글6");

        commentService.resisterSuper(memberLee, post4, "댓글7");
        commentService.resisterSuper(memberTester, post4, "댓글8");

        commentService.resisterSuper(memberLee, post5, "댓글9");
        commentService.resisterSuper(memberTester, post5, "댓글10");

        commentService.resisterSuper(memberLee, post6, "댓글11");
        commentService.resisterSuper(memberTester, post6, "댓글12");
    }
}
