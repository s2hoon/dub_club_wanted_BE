package com.likelion.dub.controller;

import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.dto.PostWritingRequest;
import com.likelion.dub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    /**
     * 동아리 글 전체 조회
     * @param
     * @return all post
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Post>> getAllClubs() {
        return ResponseEntity.ok().body(postService.getAllClubs());

    }

    /**
     * post 작성
     * @param dto
     * @return
     */

    @PostMapping("/write-post")
    public ResponseEntity<String> writePost(@RequestBody PostWritingRequest dto) {
        postService.writePost(dto.getClubName(),dto.getTitle(), dto.getContent());
        return ResponseEntity.ok().body("글 등록 완료");
    }

    /**
     * post 보기
     * @param id
     * @return
     */
    @GetMapping("/read-post")
    public ResponseEntity<Post> readPost(@RequestParam(value= "id", required = true) Long id){
        return ResponseEntity.ok().body(postService.readPost(id));
    }
}
