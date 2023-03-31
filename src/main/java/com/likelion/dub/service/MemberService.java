package com.likelion.dub.service;


import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.Role;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.ClubRepository;
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
    private final ClubRepository clubRepository;


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
        return !member.isPresent();
    }


    public void join(String email, String name, String password, Long stunum, String role) {
        //email 중복 check
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                        throw new BaseException(BaseResponseStatus.EMAIL_ALREADY_EXIST);
                });





        //저장
        if (role.equals("CLUB")) {

            Club club = Club.builder()
                    .clubName(name)
                    .build();

            Member member = Member.builder()
                    .email(email)
                    .password(encoder.encode(password))
                    .stunum(stunum)
                    .name(name)
                    .role(role)
                    .club(club)
                    .build();

            clubRepository.save(club);
            memberRepository.save(member);


        } else if (role.equals("USER")) {
            Member member = Member.builder()
                    .email(email)
                    .password(encoder.encode(password))
                    .stunum(stunum)
                    .role(role)
                    .name(name)
                    .build();
            memberRepository.save(member);
        } else if (role.equals("ADMIN")) {
            //admin 나중에 구현
        }


    }


    public String login(String email, String password) throws BaseException {
        //email 없음
        Member selectedUser = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));

        //비밀번호 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
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

}

