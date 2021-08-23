package springpractice.springblog.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springpractice.springblog.domain.Member;
import springpractice.springblog.web.form.LoginForm;
import springpractice.springblog.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm,
                        @RequestParam(defaultValue = "/") String requestURL,
                        BindingResult result,
                        HttpServletRequest request) {
        if (result.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm);

        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        log.info("로그인 성공 [{}]", loginMember.getUserId());

        return "redirect:" + requestURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
