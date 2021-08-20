package springpractice.springblog.repository.spd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springpractice.springblog.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //Member save(Member member);

    Optional<Member> findByUserId(String id);

    //List<Member> findAll();
}
