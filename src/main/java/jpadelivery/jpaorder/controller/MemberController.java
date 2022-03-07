package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.domain.Address;
import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    @Autowired
    private JavaMailSender sender;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        member.setAddress(address);
        member.setPhone(form.getPhone());
        memberService.join(member);
        return "redirect:/"; //home으로
    }

    @GetMapping("/member_chk")
    @ResponseBody
    public int memberChk(String email) throws Exception{
        int cnt = 0;
        cnt = memberService.validateDuplicateMember(email);
        return cnt;
    }

    @GetMapping("/mail_verification_code")
    @ResponseBody
    public int mailVerificationCode(String email) throws Exception{
        return memberService.mailVerificationCode(email);
    }



}
