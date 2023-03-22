package com.likelion.dub.service;


import com.likelion.dub.domain.Member;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.MemberRepository;
import com.likelion.dub.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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



    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L;



    public String join(String email, String username, String password,Long stu_num){
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
                .stu_num(stu_num)
                .build();

        memberRepository.save(member);


        return "SUCCESS";
    }


    public String login(String email, String password) {
        //email 없음
        Member selectedUser = memberRepository.findByEmail(email)
                .orElseThrow(()-> new AppException(Errorcode.USERNAME_DUPLICATED, email + "이 없습니다."));

        //비밀번호 틀림

        //

        String token = JwtTokenUtil.createToken(selectedUser.getEmail(), key, expireTimeMs);


        return token;
    }

}
