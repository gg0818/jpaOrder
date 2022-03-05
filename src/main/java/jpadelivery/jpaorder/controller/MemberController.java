package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.domain.Address;
import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Random;
import java.util.logging.Logger;

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

    @GetMapping("/mail_chk")
    @ResponseBody
    public int mailChk(String email) throws Exception{
        int cnt = 0;
        cnt = memberService.validateDuplicateMember(email);
        return cnt;
    }

    @GetMapping("/mail_verification_code")
    @ResponseBody
    public int mailVerificationCode(String email) throws Exception{
        log.info("이메일 확인 : " + email);
        Random random = new Random();
        int verificationCode = random.nextInt(888888)+111111;
        String setFrom = "gihyun0818@gmail.com";
        String toMail = email;
        String title = "회원가입 인증 이메일입니다. :D";
        String content = "홈페이지를 방문해주셔서 감사합니다." +
                "<br><br> 인증번호는" + verificationCode + "입니다. <br> 해당 인증번호를 입력해주세요.";
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            sender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

        return verificationCode;
    }
}
