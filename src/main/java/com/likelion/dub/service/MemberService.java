package com.likelion.dub.service;


import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Role;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.MemberRepository;
import com.likelion.dub.utils.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;


    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; //1시간


    public boolean checkEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return !member.isPresent();
    }


    public boolean checkStunum(Long stunum) {
        Optional<Member> member = memberRepository.findByStunum(stunum);
        log.info(member.get().getStunum().toString());
        return !member.isPresent();
    }


    public String join(String email, String username, String password, Long stunum, String role) {
        //email 중복 check
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new AppException(Errorcode.USERNAME_DUPLICATED, email + "는 이미 있습니다");
                });
        //저장
        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(encoder.encode(password))
                .stunum(stunum)
                .role(role)
                .build();

        memberRepository.save(member);


        return "SUCCESS";
    }


    public String login(String email, String password) {
        //email 없음
        Member selectedUser = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(Errorcode.USERNAME_DUPLICATED, email + "이 없습니다."));


        //비밀번호 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(Errorcode.INVALID_PASSWORD, "패스워드를 잘못 입력했습니다");
        }

        String token = JwtTokenUtil.createToken(selectedUser.getEmail(),selectedUser.getRole(), key, expireTimeMs);

        return token;
    }


    public void changePassword(Long id, String password) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id " + id));
        member.setPassword(password);
        memberRepository.save(member);
    }

    public boolean checkIdEquals(Authentication authentication, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id " + id));
        return member.getUsername().equals(authentication.getName());


    }
}

