package springpractice.springblog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Member;
import springpractice.springblog.repository.spd.BlogRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    BlogRepository blogRepository;

    @Test
    public void 회원저장() throws Exception {
        //given

        //when
        Member member = memberService.join("ldk", "1802", "lee");

        //then
        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(1);
        assertThat(memberService.findOne("ldk").get()).isEqualTo(member);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복아이디검사() throws Exception {
        //given
        memberService.join("ldk", "1802", "lee");

        //when
        memberService.join("ldk", "1805", "kim");

        //then
        fail();
    }

    @Test
    public void 회원저장_블로그자동생성() throws Exception {
        //given
        Member member1 = memberService.join("ldk1", "1802", "lee");
        Member member2 = memberService.join("ldk2", "1802", "kim");

        //when
        List<Blog> blogs = blogRepository.findAll();

        //then
        assertThat(blogs.size()).isEqualTo(2);
        assertThat(member1.getBlog().getTitle()).isEqualTo("lee님의 블로그");
        assertThat(member2.getBlog().getTitle()).isEqualTo("kim님의 블로그");
    }

    @Test
    public void 블로그_이름변경() throws Exception {
        //given
        Member member = memberService.join("ldk", "1802", "lee");

        //when
        member.getBlog().changeTitle("개발일지");

        //then
        assertThat(blogRepository.findAll().get(0).getTitle()).isEqualTo("개발일지");
    }
}