package com.likelion.dub.post.controller;

import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponse;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import com.likelion.dub.post.domain.GetAllPostResponse;
import com.likelion.dub.post.domain.GetOnePostResponse;
import com.likelion.dub.post.domain.PostEditRequest;
import com.likelion.dub.post.domain.WritingRequest;
import com.likelion.dub.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;


    @GetMapping("/getAll")
    public BaseResponse<List<GetAllPostResponse>> getAllPost() {
        try {
            List<GetAllPostResponse> getAllPostResponses = postService.getAllPost();
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, getAllPostResponses);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @PostMapping(value = "/write-post")
    public BaseResponse<String> writePost(@ModelAttribute WritingRequest writingRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            String title = writingRequest.getTitle();
            String content = writingRequest.getContent();
            MultipartFile file = writingRequest.getImage();
            postService.writing(email, title, content, file);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, "글 작성 성공");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * post 보기
     *
     * @param id
     * @return
     */
    @GetMapping("/read-post/{id}")
    public BaseResponse<GetOnePostResponse> readPost(@PathVariable Long id) throws BaseException {

        try {
            GetOnePostResponse getOnePostResponse = postService.readPost(id);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, getOnePostResponse);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }


    @DeleteMapping("delete-post")
    public BaseResponse<String> deletePost(@RequestParam(value = "id") Long id) {
        postService.deletePost(id);
        String result = "동아리 게시글 삭제 완료";
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
    }

    @PutMapping("/edit-post")
    public BaseResponse<String> editPost(@RequestPart(value = "json") PostEditRequest dto,
                                         @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        String newTitle = dto.getTitle();
        String newContent = dto.getContent();
        int newCategory = dto.getCategory();
        List<MultipartFile> newImages = images;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //jwt token 오류
        if (authentication == null || !authentication.isAuthenticated()) {
            return new BaseResponse(BaseResponseStatus.JWT_TOKEN_ERROR);
        }
        String email = authentication.getName();

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}
