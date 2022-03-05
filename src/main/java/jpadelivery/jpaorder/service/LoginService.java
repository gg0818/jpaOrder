package jpadelivery.jpaorder.service;

import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * @return null 이면 로그인 실패
     */
    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .stream().filter(m -> m.getPassword()
                        .equals(password)).findFirst().orElse(null);
    }
}
