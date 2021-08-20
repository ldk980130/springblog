package springpractice.springblog.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String name;

    @JoinColumn(name = "blog_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Blog blog;

    private void setBlog(Blog blog) {
        this.blog = blog;

    }

    protected Member() {
    }
    public static Member create(String userId, String password,String name) {
        Member member = new Member();
        member.userId = userId;
        member.password = password;
        member.name = name;

        Blog blog = Blog.create(member);
        member.blog = blog;

        return member;
    }
}
