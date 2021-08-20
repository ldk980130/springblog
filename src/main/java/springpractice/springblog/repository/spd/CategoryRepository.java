package springpractice.springblog.repository.spd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Category;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Category save(Category category);

    //Optional<Category> findById(Long id);

    //List<Category> findAll();

    //void delete(Category category);

    Optional<Category>findByBlogAndName(Blog blog, String name);
}
