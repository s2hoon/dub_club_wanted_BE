package com.likelion.dub.controller;

import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.domain.dto.MemberLoginRequest;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 이메일 중복체크
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<String> checkEmail(@PathVariable String email) {


        boolean isEmailAvailable = memberService.checkEmail(email);
        if(isEmailAvailable) {
            return ResponseEntity.ok().body("이메일 사용 가능");
        } else {
            return ResponseEntity.ok().body("해당 이메일이 이미 사용 중입니다");
        }
    }

    /**
     * 학번 중복체크
     * @param stunum
     * @return
     */
    @GetMapping("/stunum/{stunum}")
    public ResponseEntity<String> checkStunum(@PathVariable Long stunum) {
        boolean isStunumAvailable = memberService.checkStunum(stunum);
        if(isStunumAvailable) {
            return ResponseEntity.ok().body("학번 사용 가능");
        } else {
            return ResponseEntity.ok().body("해당 학번이 이미 사용 중입니다");
        }
    }


    /**
     * 회원가입
     * @param dto
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<String> join(@RequestBody MemberJoinRequest dto) {

        memberService.join(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getStunum(), dto.getRole());
        return ResponseEntity.ok().body("회원가입이 성공 했습니다.");
    }

    /**
     * 로그인
     * @param dto
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest dto) {
        String token = memberService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body("로그인이 성공했습니다. " + "token: Bearer "+ token);
    }

    /**
     * 비밀번호 수정
     * @param id
     * @param password
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String password) {
        try{
            memberService.changePassword(id, password);
            return ResponseEntity.ok().body("비밀번호 수정완료");
        }catch(Exception e){
            return ResponseEntity.ok().body("비밀번호 수정실패");
        }

    }

    /**
     * test api
     * @param dto
     * @return
     */
    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody MemberJoinRequest dto) {
        return ResponseEntity.ok().body("test 성공");
    }
}
