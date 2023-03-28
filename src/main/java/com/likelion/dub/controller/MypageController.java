package com.likelion.dub.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.dto.MyPageResponse;
import com.likelion.dub.domain.dto.PasswordRequest;
import com.likelion.dub.service.MemberService;
import com.likelion.dub.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/mypage")
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    private final MypageService mypageService;


    /**
     * 마이페이지 정보조회
     * @return
     */
    @GetMapping("/getInfo")
    public BaseResponse<MyPageResponse> getMyPage(){
        try {
            //Spring Security Context 에서 인증 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new BaseResponse(BaseResponseStatus.INVALID_MEMBER_JWT);
            }
            //사용자 정보를 추출하여 마이페이지 정보 반환
            String email = authentication.getName();
            Member member = mypageService.loadMemberByEmail(email);

            log.info(email.toString());
            log.info(member.toString());

            MyPageResponse myPageResponse = new MyPageResponse(member.getEmail(), member.getUsername(), member.getStunum(), member.getRole());
            return new BaseResponse<>(myPageResponse);
        } catch (BaseException e) {
            return new BaseResponse(BaseResponseStatus.JWT_TOKEN_ERROR);
        }

    }


    /**
     * 비밀번호 수정
     * @param passwordRequest
     * @return
     */
    @PutMapping("/password")
    public BaseResponse<String> changePassword(@RequestBody PasswordRequest passwordRequest) {
        String currentPassword = passwordRequest.getCurrentPassword();
        String newPassword = passwordRequest.getNewPassword();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //jwt token 오류
        if (authentication == null || !authentication.isAuthenticated()) {
            return new BaseResponse(BaseResponseStatus.JWT_TOKEN_ERROR);
        }
        String email = authentication.getName();
        Member member = mypageService.loadMemberByEmail(email);
        //current password wrong
        if (!BCrypt.checkpw(passwordRequest.getCurrentPassword(), member.getPassword())) {
            return new BaseResponse(BaseResponseStatus.WRONG_PASSWORD);
        }
        //새로운 비밀번호 암호화하여 업데이트
        String hashedPassword = BCrypt.hashpw(passwordRequest.getNewPassword(), BCrypt.gensalt());
        member.setPassword(hashedPassword);
        mypageService.save(member);

        String result = "비밀번호 수정 완료";
        return new BaseResponse<>(result);

    }

}
