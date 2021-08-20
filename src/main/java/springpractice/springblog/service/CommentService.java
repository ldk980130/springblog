package springpractice.springblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Comment;
import springpractice.springblog.domain.Member;
import springpractice.springblog.domain.Post;
import springpractice.springblog.repository.spd.CommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment resisterSuper(Member member, Post post, String content) {
        Comment comment = Comment.createSuper(member, post, content);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Comment resisterSub(Member member, Comment commentSuper, String content) {
        Comment comment = Comment.createSub(member, commentSuper, content);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void edit(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.edit(content);
    }

    public Comment findOne(Long id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Transactional
    public void delete(Comment comment) {

        if (comment.getSuperComment() != null || comment.getSubComment().size() == 0) {
            comment.removeRelations();
            commentRepository.delete(comment);
        }
        else {
            comment.delete();
        }
    }
}
