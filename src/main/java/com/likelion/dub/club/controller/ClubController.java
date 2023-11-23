package com.likelion.dub.club.controller;


import com.likelion.dub.club.service.ClubService;
import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponse;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/club")
@RequiredArgsConstructor
public class ClubController {


    private final ClubService clubService;


    @PostMapping("/uploadForm")
    @PreAuthorize("hasAnyRole('CLUB')")
    public BaseResponse<String> uploadForm(@RequestBody String url, Principal principal) {
        try {
            String email = principal.getName();
            clubService.uploadForm(url, email);
            String result = "동아리 지원서 양식 등록 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/writeIntro")
    @PreAuthorize("hasAnyRole('CLUB')")
    public BaseResponse<String> writeInfo(@RequestBody String introduction, Principal principal) {
        try {
            String email = principal.getName();
            clubService.updateIntroduce(introduction, email);
            String result = "동아리 소개글 작성 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/uploadClubImage")
    @PreAuthorize("hasAnyRole('CLUB')")
    public BaseResponse<String> uploadClubImage(@ModelAttribute MultipartFile image, Principal principal) {
        try {
            String email = principal.getName();
            clubService.updateClubImage(image, email);
            String result = "동아리 사진 등록 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

  
}
