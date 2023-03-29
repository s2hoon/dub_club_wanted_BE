package com.likelion.dub.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.dto.PostWritingRequest;
import com.likelion.dub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/app/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //Cors 제거
public class PostController {
    private final PostService postService;


    /**
     * 동아리 글 전체 조회
     * @param
     * @return all post
     */
    @GetMapping("/getAll")
    public BaseResponse<List<Post>> getAllClubs() {
        return new BaseResponse<>(postService.getAllClubs());

    }

    /**
     * post 작성
     * @param dto
     * @return
     */

    @PostMapping("/write-post")
    public BaseResponse<String> writePost(@RequestPart(value = "json") PostWritingRequest dto, @RequestPart(value="images", required = false)List<MultipartFile> files) throws BaseException {
        try {
            postService.writePost(dto.getClubName(), dto.getTitle(), dto.getContent(), dto.getCategory(), files);
            return new BaseResponse<>("글 작성 성공");
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * post 보기
     * @param id
     * @return
     */
    @GetMapping("/read-post")
    public BaseResponse<Post> readPost(@RequestParam(value= "id", required = true) Long id) throws BaseException {
        return new BaseResponse<>(postService.readPost(id));
    }
}
