package springpractice.springblog.repository.spd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Post;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //Post save(Post post);

    //Optional<Post> findById(Long id);

    //List<Post> findAll();

    //void delete(Post post);

    List<Post> findAll();

    Page<Post> findAll(Pageable pageable);

    List<Post> findAllByCategoryBlog(Blog blog, Sort sort);

    List<Post> findAllByCategoryBlog(Blog blog, Pageable pageable);
}
