package jpadelivery.jpaorder.service;

import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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

    public int validateDuplicateMember(String email){
        int cnt = 0;
        List<Member> findEmails = memberRepository.findByEmail(email);
        if(!findEmails.isEmpty()){
            cnt=1;
        }
        return cnt;
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


}
