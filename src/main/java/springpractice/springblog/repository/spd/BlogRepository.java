package springpractice.springblog.repository.spd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springpractice.springblog.domain.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
