package springpractice.springblog.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired MemberService memberService;
    @Autowired CategoryService categoryService;
    @Autowired PostService postService;

    @Test
    public void 댓글등록() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");

        //when
        commentService.resisterSuper(member, post, "어렵지");

        //then
        List<Comment> all = commentService.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");
        Comment comment = commentService.resisterSuper(member, post, "어렵지");

        //when
        commentService.delete(comment);

        //then
        List<Comment> all = commentService.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");
        Comment comment = commentService.resisterSuper(member, post, "어렵지");

        //when
        Long id = comment.getId();
        commentService.edit(id, "너무너무어려웡");

        //then
        Comment one = commentService.findOne(id);
        assertThat(one.getContent()).isEqualTo("너무너무어려웡");
    }

    @Test
    public void 대댓글_등록() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");
        Comment comment = commentService.resisterSuper(member, post, "어렵지");

        //when
        Comment subComment = commentService.resisterSub(member, comment, "아닌데");

        //then
        Comment findOne = commentService.findOne(subComment.getId());
        Comment findSubComment = comment.getSubComment().get(0);
        assertThat(findOne).isEqualTo(findSubComment);
    }

    @Test
    public void 대댓글_삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");
        Comment comment = commentService.resisterSuper(member, post, "어렵지");
        Comment subComment = commentService.resisterSub(member, comment, "아닌데");

        //when
        commentService.delete(subComment);

        //then
        List<Comment> all = commentService.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void 게시글삭제_댓글다삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");
        Comment comment1 = commentService.resisterSuper(member, post, "어렵지");
        Comment comment2 = commentService.resisterSuper(member, post, "어렵지");
        Comment comment3 = commentService.resisterSuper(member, post, "어렵지");

        //when
        postService.delete(post);

        //then
        List<Comment> all = commentService.findAll();
        assertThat(all.size()).isEqualTo(0);
    }


    @Test
    public void 대댓_가지는_댓글삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1234", "lee");
        Category category1 = categoryService.resister(member.getBlog(), "백엔드");
        Post post = postService.resister(category1, "스프링", "어려워");
        Comment comment = commentService.resisterSuper(member, post, "어렵지");
        Comment subComment1 = commentService.resisterSub(member, comment, "아님");
        Comment subComment2 = commentService.resisterSub(member, comment, "아님");
        Comment subComment3 = commentService.resisterSub(member, comment, "아님");

        //when
        commentService.delete(comment);

        //then
        List<Comment> all = commentService.findAll();
        assertThat(all.size()).isEqualTo(4);
        assertThat(comment.getStatus()).isEqualTo(Status.Delete);
        assertThat(comment.getContent()).isEqualTo("삭제된 댓글입니다.");
    }
}