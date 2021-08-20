package springpractice.springblog.domain;

import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Post implements Comparable<Post>{

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    private String createTime;
    //테스트용
    public void setCreateTime(String date) {
        this.createTime = date;
    }

    private String editTime;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    /*=연관관계 편의 메서드=*/
    private void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }

    protected Post() {
    }

    /*=생성 메서드=*/
    public static Post create(Category category, String title, String content) {
        Post post = new Post();
        post.setCategory(category);
        post.title = title;
        post.content = content;
        post.createTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date());
        return post;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
        this.editTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date()) + "(수정)";
    }

    public void removeRelations() {
        this.category.getPosts().remove(this);
        this.category = null;
    }

    public void changeCategory(Category category) {
        removeRelations();
        this.category = category;
        category.getPosts().add(this);
    }

    @Override
    public int compareTo(Post other) {
        return this.createTime.compareTo(other.createTime);
    }
}
