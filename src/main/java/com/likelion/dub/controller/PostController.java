package com.likelion.dub.controller;

import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.dto.*;
import com.likelion.dub.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/app/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //Cors 제거
@Slf4j
public class PostController {
    private final PostService postService;


    /**
     * 동아리글 전체 조회
     * @return
     */
    @GetMapping("/getAll")
    public BaseResponse<List<GetAllPostResponse>> getAllPost() {
        try{
            List<GetAllPostResponse> getAllPostResponses = postService.getAllPost();
            return new BaseResponse<>(BaseResponseStatus.SUCCESS,getAllPostResponses);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    /**
     * post 작성
     * @param writingRequest
     * @return
     */

    @PostMapping(value = "/write-post",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<String> writePost(@ModelAttribute WritingRequest writingRequest) {
        try {
            String title =  writingRequest.getTitle();
            String content =  writingRequest.getContent();
            MultipartFile file = writingRequest.getImage();
            postService.writing(title, content, file);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS,"글 작성 성공");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
    /**
     * post 보기
     * @param id
     * @return
     */
    @GetMapping("/read-post/{id}")
    public BaseResponse<GetOnePostResponse> readPost(@PathVariable Long id) throws BaseException {

        try{
            GetOnePostResponse getOnePostResponse = postService.readPost(id);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, getOnePostResponse);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }


    @DeleteMapping("delete-post")
    public BaseResponse<String> deletePost(@RequestParam(value="id") Long id)  {
        postService.deletePost(id);
        String result = "동아리 게시글 삭제 완료";
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,result);
    }
    @PutMapping("/edit-post")
    public BaseResponse<String> editPost(@RequestPart(value="json") PostEditRequest dto, @RequestPart(value="images", required = false) List<MultipartFile> images)  {
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
