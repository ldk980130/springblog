package springpractice.springblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springpractice.springblog.domain.Member;
import springpractice.springblog.web.form.LoginForm;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    public Member login(LoginForm loginForm) {
        return memberService.findOne(loginForm.getLoginId())
                .filter(m -> m.getPassword().equals(loginForm.getPassword()))
                .orElse(null);
    }
}
