package com.likelion.dub.member.controller;

import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponse;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import com.likelion.dub.common.util.SecurityUtil;
import com.likelion.dub.member.dto.request.MemberJoinRequest;
import com.likelion.dub.member.dto.request.MemberLoginRequest;
import com.likelion.dub.member.dto.request.ToClubRequest;
import com.likelion.dub.member.service.MemberService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 테스트 api
    @GetMapping("/testing")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public BaseResponse<String> testing() {
        Optional<String> email = SecurityUtil.getCurrentUsername();
        log.info("email = {}", email);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, "테스트 성공");
    }

    // 이메일 중복체크
    @GetMapping("/email/{email}")
    public BaseResponse<String> checkEmail(@PathVariable String email) {
        try {
            memberService.checkEmail(email);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, "이메일 사용 가능");
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }
    }

    // 일반 회원가입
    @PostMapping("/sign-up")
    public BaseResponse<String> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        try {
            memberService.join(memberJoinRequest);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, "(일반) 회원 가입 완료");
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }
    }

    // 일반 로그인
    @PostMapping("/sign-in")
    public BaseResponse<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        try {
            String token = memberService.login(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, "Bearer " + token);

        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }

    }

    // 동아리 회원으로 전환
    @PostMapping("/toClub")
    public BaseResponse<String> toClub(@RequestBody ToClubRequest toClubRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            memberService.transferToClub(email, toClubRequest);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, "동아리 회원으로 전환 완료");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

//    // 카카오 로그인
//    @PostMapping("/loginKakao")
//    public BaseResponse<String> loginKakao(@RequestBody KakaoLoginParams params) {
//        try {
//            log.info("params = {}", params.getAuthorizationCode());
//            String token = memberService.loginKakao(params);
//            log.info("JWT token = {}", token);
//
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS, "Bearer " + token);
//        } catch (BaseException e) {
//            return new BaseResponse(e.getStatus());
//        }
//    }
//
//
//    // 회원 정보 조회
//    @GetMapping("/getInfo")
//    public BaseResponse<GetMemberInfoResponse> getInfo() {
//
//        try {
//            GetMemberInfoResponse getMemberInfoResponse = memberService.getInfo();
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS, getMemberInfoResponse);
//        } catch (BaseException e) {
//            return new BaseResponse(e.getStatus());
//        }
//    }
//
//
//    // 비밀번호 수정
//    @PutMapping("/changePwd")
//    public BaseResponse<String> changePwd(@RequestBody ChangePwdRequest changePwdRequest) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String email = authentication.getName();
//            memberService.changePassword(email, changePwdRequest.getCurrentPassword(),
//                    changePwdRequest.getNewPassword());
//            String result = "비밀번호 수정 완료";
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
//        } catch (BaseException e) {
//            return new BaseResponse(e.getStatus());
//        }
//
//
//    }
}
