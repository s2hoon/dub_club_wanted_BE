package com.likelion.dub.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.domain.dto.MemberLoginRequest;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.likelion.dub.common.BaseResponseStatus.WRONG_EMAIL;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //Cors 제거
public class MemberController {

    private final MemberService memberService;

    @GetMapping("testcicd")
    public BaseResponse<String> testcicd() {
        return new BaseResponse<>("test complete");
    }


    /**
     * 이메일 중복체크
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public BaseResponse<String> checkEmail(@PathVariable String email) {

        boolean isEmailAvailable = memberService.checkEmail(email);
        if (isEmailAvailable) {
            String result = "이메일 사용 가능";
            return new BaseResponse<>(result);
        } else {
            return new BaseResponse(BaseResponseStatus.EMAIL_ALREADY_EXIST);
        }

    }

    /**
     * 학번 중복체크
     * @param stunum
     * @return
     */
    @GetMapping("/stunum/{stunum}")
    public BaseResponse<String> checkStunum(@PathVariable Long stunum) {
        boolean isStunumAvailable = memberService.checkStunum(stunum);
        if(isStunumAvailable) {
            String result = "학번 사용 가능";
            return new BaseResponse<>(result);
        } else {
            return new BaseResponse(BaseResponseStatus.STU_NUM_ALREADY_EXIST);
        }
    }


    /**
     * 회원가입
     * @param dto
     * @return
     */
    @PostMapping("/sign-up")
    public BaseResponse<String> join(@RequestBody MemberJoinRequest dto ) throws BaseException{
            try {
                memberService.join(dto.getEmail(), dto.getName(), dto.getPassword(), dto.getStunum(), dto.getRole());
                String result = "회원 가입 완료";
                return new BaseResponse<>(result);
            }
            catch(BaseException e){
                return new BaseResponse(e.getStatus());
            }
    }

    /**
     * 로그인
     * @param dto
     * @return
     */
    @PostMapping("/sign-in")
    public BaseResponse<String> login(@RequestBody MemberLoginRequest dto) {
        try {
            String token = memberService.login(dto.getEmail(), dto.getPassword());
            return new BaseResponse<>("Bearer "+token);

        } catch (BaseException e) {
            BaseResponseStatus result = e.getStatus();
            return new BaseResponse(result);
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
