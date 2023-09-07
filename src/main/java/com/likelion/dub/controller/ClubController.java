package com.likelion.dub.controller;


import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
