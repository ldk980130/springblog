package springpractice.springblog.domain;

import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String createTime;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_comment_id")
    private Comment superComment;

    @OneToMany(mappedBy = "superComment", cascade = CascadeType.ALL)
    private List<Comment> subComment = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    /*=연관관계 편의 메서드=*/
    private void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    private void setSuperComment(Comment superComment) {
        this.superComment = superComment;
        superComment.getSubComment().add(this);
    }

    private void setMember(Member member) {
        this.member = member;
    }

    protected Comment() {
    }

    /*=생성 메서드=*/
    public static Comment createSuper(Member member, Post post, String content) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.content = content;
        comment.createTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date());
        comment.status = Status.Exist;
        return comment;
    }

    public static Comment createSub(Member member, Comment superComment, String content) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setSuperComment(superComment);
        comment.content = content;
        comment.createTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date());
        comment.status = Status.Exist;
        return comment;
    }

    public void edit (String content) {
        this.content = content;
        this.createTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date()) + "(수정)";
    }

    public boolean isSuper() {
        if (this.post != null) return true;
        else return false;
    }

    public void removeRelations() {

        if (this.isSuper()) {
            this.post.getComments().remove(this);
            this.post = null;
        }
        else {
            this.superComment.getSubComment().remove(this);
            this.superComment = null;
        }

        this.member = null;
    }

    public void delete() {
        this.status = Status.Delete;
        this.content = "삭제된 댓글입니다.";
    }
}
