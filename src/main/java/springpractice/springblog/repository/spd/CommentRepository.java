package springpractice.springblog.repository.spd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springpractice.springblog.domain.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Comment save(Comment comment);

    //Optional<Comment> findById(Long id);

    //List<Comment> findAll();

    //void delete(Comment comment);
}
