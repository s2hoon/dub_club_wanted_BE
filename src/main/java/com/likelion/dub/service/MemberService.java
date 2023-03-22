package com.likelion.dub.service;


import com.likelion.dub.domain.Member;
import com.likelion.dub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;


    public String join(String email, String username, String password,Long stu_num){
        //email 중복 check
        memberRepository.findByEmail(email)
                .ifPresent(member -> {throw new RuntimeException(email + "는 이미 있습니다");
    });
    //저장
        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(encoder.encode(password))
                .stu_num(stu_num)
                .build();

        memberRepository.save(member);


        return "SUCCESS";
    }


    public String sign_in(String email, String password) {
        //email 없음

        //비밀번호 틀림

        //



        return "token";
    }

}
