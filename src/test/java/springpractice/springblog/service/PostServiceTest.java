package springpractice.springblog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Category;
import springpractice.springblog.domain.Member;
import springpractice.springblog.domain.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired PostService postService;
    @Autowired MemberService memberService;
    @Autowired CategoryService categoryService;

    @Test
    public void 게시글생성() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category = categoryService.resister(blog, "백엔드");

        //when
        Post findPost = postService.resister(category, "자바", "객체지향");

        //then
        assertThat(findPost).isNotNull();
        assertThat(findPost.getTitle()).isEqualTo("자바");
    }

    @Test
    public void 게시글삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category = categoryService.resister(blog, "백엔드");

        //when
        Post post = postService.resister(category, "a", "a");
        postService.delete(post);

        //then
        assertThat(postService.findAll().size()).isEqualTo(0);
    }

    @Test
    public void 카테고리삭제_게시글_다_삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category = categoryService.resister(blog, "백엔드");

        //when
        postService.resister(category, "a", "a");
        postService.resister(category, "b", "a");
        categoryService.delete(category);

        //then
        List<Post> all = postService.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    public void 게시글_수정() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category = categoryService.resister(blog, "백엔드");
        Post post = postService.resister(category, "a", "a");

        //when
        postService.edit(post.getId(), "b", "B");

        //then
        String title = postService.findOne(post.getId()).getTitle();
        String content = postService.findOne(post.getId()).getContent();
        assertThat(title).isEqualTo("b");
        assertThat(content).isEqualTo("B");
    }

    @Test
    public void 블로그내_모든_게시글_출력() throws Exception {
        //given
        Member member1 = memberService.join("ldk", "1802", "lee");
        Blog blog1 = member1.getBlog();
        Category category1 = categoryService.resister(blog1, "백엔드");
        Post postA = postService.resister(category1, "a", "a");
        Post postB = postService.resister(category1, "b", "b");
        Post postC = postService.resister(category1, "c", "c");

        Member member2 = memberService.join("ldk1", "1802", "lee");
        Blog blog2 = member2.getBlog();
        Category category2 = categoryService.resister(blog2, "백엔드");
        Post postD = postService.resister(category2, "d", "d");

        //when
        List<Post> allPost = postService.findAll();
        List<Post> allFromBlog1 = postService.findAllByBlog(blog1);
        List<Post> allFromBlog2 = postService.findAllByBlog(blog2);

        //then
        assertThat(allPost.size()).isEqualTo(4);
        assertThat(allFromBlog1.size()).isEqualTo(3);
        assertThat(allFromBlog2.size()).isEqualTo(1);
    }

    @Test
    public void 블로그내_모든_게시물_날짜순_출력() throws Exception {
        //given
        Member member1 = memberService.join("ldk", "1802", "lee");
        Blog blog = member1.getBlog();
        Category category1 = categoryService.resister(blog, "백엔드");
        Post postA = postService.resister(category1, "a", "a");
        postA.setCreateTime("2021-08-16 00:08");
        Post postB = postService.resister(category1, "b", "b");
        postB.setCreateTime("2021-08-16 00:05");
        Post postC = postService.resister(category1, "c", "c");
        postC.setCreateTime("2020-08-16 00:05");

        //when
        List<Post> posts = postService.findAllByBlog(blog);

        //then
        assertThat(posts.get(0)).isEqualTo(postC);
        assertThat(posts.get(1)).isEqualTo(postB);
        assertThat(posts.get(2)).isEqualTo(postA);
    }

    @Test
    public void 카테고리_변경() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category1 = categoryService.resister(blog, "백엔드");
        Category category2 = categoryService.resister(blog, "프론트엔드");
        Post post = postService.resister(category1, "자바", "어려워");

        //when
        Post findPost = postService.findOne(post.getId());
        postService.moveToAnotherCategory(findPost.getId(), category2);

        //then
        assertThat(findPost.getCategory()).isEqualTo(category2);
        assertThat(category2.getPosts().get(0)).isEqualTo(findPost);
    }

    @Test
    public void 페이징() throws Exception {
        //given
        Member member1 = memberService.join("ldk", "1802", "lee");
        Blog blog = member1.getBlog();
        Category category1 = categoryService.resister(blog, "백엔드");
        Post postA = postService.resister(category1, "a", "a");
        postA.setCreateTime("2021-08-16 00:08");
        Post postB = postService.resister(category1, "b", "b");
        postB.setCreateTime("2021-08-16 00:05");
        Post postC = postService.resister(category1, "c", "c");
        postC.setCreateTime("2020-08-16 00:05");

        //when
        List<Post> page = postService.findPageByBlog(blog, 0, 2);

        //then
        assertThat(page.size()).isEqualTo(2);
        assertThat(page.get(0)).isEqualTo(postC);
        assertThat(postA).isNotIn(page);
    }
}