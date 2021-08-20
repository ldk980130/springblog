package springpractice.springblog.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @JoinColumn(name = "blog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Blog blog;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    /*=연관관계 편의 메서드=*/
    private void setBlog(Blog blog) {
        this.blog = blog;
        blog.getCategories().add(this);
    }

    protected Category(){
    }

    /*=생성 메서드=*/
    public static Category create(Blog blog, String name) {
        Category category = new Category();
        category.setBlog(blog);
        category.name = name;
        return category;
    }

    public void editName (String name) {
        this.name = name;
    }

    public void removeRelations() {
        this.blog.getCategories().remove(this);
        this.blog = null;
    }
}
