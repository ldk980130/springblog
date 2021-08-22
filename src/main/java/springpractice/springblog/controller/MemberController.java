package springpractice.springblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springpractice.springblog.form.MemberForm;
import springpractice.springblog.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute MemberForm memberForm) {
        return "members/addMemberForm";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {
            return "members/addMemberForm";
        }

        memberService.join(memberForm.getUserId(), memberForm.getPassword(), memberForm.getName());
        return "redirect:/";
    }
}
