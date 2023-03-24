package com.likelion.dub.controller;

import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.dto.PostWritingRequest;
import com.likelion.dub.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Post>> getAllClubs(Model model) {

        return ResponseEntity.ok().body(postService.getAllClubs());

    }

    @PostMapping("/write-post")
    public ResponseEntity<String> writePost(@RequestBody PostWritingRequest dto) {
        postService.writePost(dto.getClubName(),dto.getTitle(), dto.getContent());
        return ResponseEntity.ok().body("글 등록 완료");
    }

    @GetMapping("/read-post")
    public ResponseEntity<Post> readPost(@RequestParam(value= "id", required = true) Long id){
        return ResponseEntity.ok().body(postService.readPost(id));
    }
}
