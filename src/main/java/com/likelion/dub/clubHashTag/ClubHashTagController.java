package com.likelion.dub.clubHashTag;


import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponse;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/club/hashTag")
@RequiredArgsConstructor
public class ClubHashTagController {
    private final ClubHashTagService clubHashTagService;


    @PostMapping("/uploadForm")
    @PreAuthorize("hasAnyRole('CLUB')")
    public BaseResponse<String> createTag(@RequestBody String tagName, Principal principal) {
        try {
            String email = principal.getName();
            clubHashTagService.createTag(tagName, email);
            String result = "태그 생성 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }


}
