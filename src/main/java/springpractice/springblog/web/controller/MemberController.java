package springpractice.springblog.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springpractice.springblog.domain.Member;
import springpractice.springblog.service.MemberService;
import springpractice.springblog.web.form.MemberForm;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute MemberForm memberForm) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {
            return "members/addMemberForm";
        }

        Member addMember;
        try {
            addMember = memberService.join(memberForm.getUserId(), memberForm.getPassword(), memberForm.getName());
            log.info("회원가입 성공 [{}]", addMember.getUserId());
        } catch (IllegalStateException e) {
            result.addError(
                    new FieldError("MemberForm", "userId", memberForm.getUserId(), false,  null, null,
                            "중복된 ID입니다.")
            );
            return "members/addMemberForm";
        }

        return "redirect:/";
    }

}
