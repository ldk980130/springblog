package springpractice.springblog.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Category;
import springpractice.springblog.domain.Member;
import springpractice.springblog.repository.spd.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired CategoryService categoryService;
    @Autowired MemberService memberService;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void 카테고리등록() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();

        //when
        categoryService.resister(blog, "백엔드");
        categoryService.resister(blog, "프론트엔드");

        //then
        List<Category> all = categoryService.findAll();
        assertThat(all.size()).isEqualTo(2);
        assertThat(blog.getCategories().size()).isEqualTo(2);
    }

    @Test(expected = IllegalStateException.class)
    public void 같은이름등록불가() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();

        //when
        categoryService.resister(blog, "백엔드");
        categoryService.resister(blog, "백엔드");

        //then
    }

    @Test
    public void 카테고리삭제() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category = categoryService.resister(blog, "백엔드");

        //when
        categoryService.delete(category);
        List<Category> all = categoryService.findAll();

        //then
        assertThat(all.size()).isEqualTo(0);
        assertThat(blog.getCategories().size()).isEqualTo(0);
    }

    @Test
    public void 카테고리_이름변경() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category = categoryService.resister(blog, "백엔드");

        //when
        categoryService.editName(category.getId(), "프론트엔드");

        //then
        assertThat(category.getName()).isEqualTo("프론트엔드");
    }

    @Test(expected = IllegalStateException.class)
    public void 이미있는_이름으로_변경() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");
        Blog blog = member.getBlog();
        Category category1 = categoryService.resister(blog, "백엔드");
        Category category2 = categoryService.resister(blog, "프론트엔드");

        //when
        categoryService.editName(category1.getId(), "프론트엔드");

        //then
    }

    @Test
    public void 다른블로그_같은카테고리이름저장() throws Exception {
        //given
        Member member1 = memberService.join("ldk", "1802", "lee");
        Member member2 = memberService.join("ldk1", "1802", "lee");
        Blog blog1 = member1.getBlog();
        Blog blog2 = member2.getBlog();

        //when
        categoryService.resister(blog1, "백엔드");
        categoryService.resister(blog2, "백엔드");

        //then
        assertThat(categoryService.findAll().size()).isEqualTo(2);
    }

}