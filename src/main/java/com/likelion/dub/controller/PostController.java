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
     * 동아리 글 전체 조회
     *
     * @param
     * @return all post
     */
    @GetMapping("/getAll")
    public BaseResponse<List<GetAllPostResponse>> getAllClubs() {
        return new BaseResponse<>(postService.getAllPost());

    }


//    /**
//     * post 작성
//     * @param dto
//     * @param file
//     * @return
//     * @throws BaseException
//     */
//    @PostMapping(value = "/write-post" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public BaseResponse<String> writePost(@RequestPart(value = "json") PostWritingRequest dto, @RequestPart(value = "image", required = false) MultipartFile file) throws BaseException {
//        try {
//            postService.writePost(dto.getTitle(), dto.getContent(), file);
//            return new BaseResponse<>("글 작성 성공");
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        } catch (IOException e){
//            return new BaseResponse<>(e.getMessage());
//        }
//    }


    /**
     * post 작성 test
     * @param writingRequest
     * @return
     */

    @PostMapping(value = "/write-post",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<String> writing(@ModelAttribute WritingRequest writingRequest) {
        try {
            String title =  writingRequest.getTitle();
            String content =  writingRequest.getContent();
            MultipartFile file = writingRequest.getImage();

            postService.writing(title, content, file);
            return new BaseResponse<>("글 작성 성공");
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
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
        return new BaseResponse<>(postService.readPost(id));
    }


    @DeleteMapping("delete-post")
    public BaseResponse<String> deletePost(@RequestParam(value="id") Long id) throws BaseException {
        postService.deletePost(id);
        String result = "동아리 게시글 삭제 완료";
        return new BaseResponse<>(result);
    }
    @PutMapping("/edit-post")
    public BaseResponse<String> editPost(@RequestPart(value="json") PostEditRequest dto, @RequestPart(value="images", required = false) List<MultipartFile> images) throws BaseException{
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
