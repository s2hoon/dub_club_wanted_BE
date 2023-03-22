package com.likelion.dub.controller;

import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.domain.dto.MemberLoginRequest;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> join(@RequestBody MemberJoinRequest dto){
        memberService.join(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getStu_num());

        return ResponseEntity.ok().body("회원가입이 성공 했습니다.");
    }
    @PostMapping("/sign-in")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest dto){
        String token = memberService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body(token);
    }

}
