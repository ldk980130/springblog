package springpractice.springblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Member;
import springpractice.springblog.repository.spd.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(String userId, String password, String name) {
        validateDuplicateMember(userId);
        Member member = Member.create(userId, password, name);

        return memberRepository.save(member);
    }

    public void validateDuplicateMember(String userId) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public Optional<Member> findOne(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
