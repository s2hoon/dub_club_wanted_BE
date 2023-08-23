package com.likelion.dub.controller;

import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.dto.ClubMemberJoinRequest;
import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.domain.dto.MemberLoginRequest;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //Cors 제거
public class MemberController {

    private final MemberService memberService;


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
            return new BaseResponse<>(BaseResponseStatus.SUCCESS,result);
        } else {
            return new BaseResponse(BaseResponseStatus.EMAIL_ALREADY_EXIST);
        }

    }


    /**
     * 일반회원가입
     * @param dto
     * @return
     */
    @PostMapping("/sign-up")
    public BaseResponse<String> join(@RequestBody MemberJoinRequest dto )  {
            try {
                
                memberService.join(dto.getEmail(), dto.getName(), dto.getPassword(), dto.getGender(), dto.getRole());
                String result = "(일반)회원 가입 완료";
                return new BaseResponse<>(BaseResponseStatus.SUCCESS,result);
            }
            catch(BaseException e){
                return new BaseResponse(e.getStatus());
            }
    }

    /**
     * 동아리회원 회원가입
     * @param dto
     * @param file
     * @return
     */
    @PostMapping(value = "/sign-up-club",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<String> joinClub(@RequestPart(value = "json") ClubMemberJoinRequest dto, @RequestPart(value = "image", required = false) MultipartFile file)  {

        try {
            memberService.joinClub(dto.getEmail(), dto.getName(), dto.getPassword(), dto.getGender(), dto.getRole(), dto.getIntroduction(), dto.getGroupName(),dto.getCategory(), file);

            String result = "(동아리)회원 가입 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS,result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    /** 로그인
     * @param dto
     * @return
     */
    @PostMapping("/sign-in")
    public BaseResponse<String> login(@RequestBody MemberLoginRequest dto) {

        try {
            String token = memberService.login(dto.getEmail(), dto.getPassword());
            return new BaseResponse<>(BaseResponseStatus.SUCCESS,"Bearer " + token);

        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }

    }



}
