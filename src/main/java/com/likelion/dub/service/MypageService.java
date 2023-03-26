package com.likelion.dub.service;


import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Member;
import com.likelion.dub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MypageService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;


//    public String loadMemberByEmail(String email) throws BaseException {
//        Member selectedUser = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
//
//
//    }

}
