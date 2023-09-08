package com.likelion.dub.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.dto.UpdateTagRequest;
import com.likelion.dub.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/club")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //Cors 제거
public class ClubController {


    private final ClubService clubService;


    @PostMapping("/uploadForm")
    public BaseResponse<String> uploadForm(@RequestBody String url) {
        try {
            clubService.uploadForm(url);
            String result = "동아리 지원서 양식 등록 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/writeIntro")
    public BaseResponse<String> writeInfo(@RequestBody String introduction) {
        try {
            clubService.updateIntroduce(introduction);
            String result = "동아리 소개글 작성 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/uploadClubImage")
    public BaseResponse<String> uploadClubImage(@ModelAttribute MultipartFile image) {
        try {
            clubService.updateClubImage(image);
            String result = "동아리 사진 등록 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/updateTag")
    public BaseResponse<String> updateTag(@RequestBody UpdateTagRequest updateTagRequest) {
        try{
            clubService.updateTag(updateTagRequest.getGroupName(), updateTagRequest.getCategory());
            String result = "동아리 태그 등록 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
}