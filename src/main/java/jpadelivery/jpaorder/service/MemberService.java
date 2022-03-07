package jpadelivery.jpaorder.service;

import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private JavaMailSender sender;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findEmails = memberRepository.findByEmail(member.getEmail());
        if(!findEmails.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    /**
     * 이메일 중복체크
     */
    public int validateDuplicateMember(String email){
        int cnt = 0;
        List<Member> findEmails = memberRepository.findByEmail(email);
        if(findEmails.isEmpty()) cnt = 1;
        return cnt;
    }

    /**
     * 이메일 인증번호 발송
     */
    public int mailVerificationCode(String email){
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
