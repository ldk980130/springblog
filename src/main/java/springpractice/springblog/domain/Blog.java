package springpractice.springblog.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Blog {

    @Id @GeneratedValue
    @Column(name = "blog_id")
    private Long id;

    private String title;

    @OneToOne(mappedBy = "blog", fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    List<Category> categories = new ArrayList<>();

    protected Blog() {
    }
    public static Blog create(Member member) {
        Blog blog = new Blog();
        blog.member = member;
        blog.title = member.getName() + "님의 블로그";
        return blog;
    }

    public void changeTitle(String title) {
        this.title = title;
    }
}
