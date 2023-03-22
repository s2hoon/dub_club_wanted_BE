package com.likelion.dub.controller;

import com.likelion.dub.domain.dto.BoardWriteRequest;
import com.likelion.dub.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/getAll")
    public ResponseEntity<String> getAllClubs() {
         boardService.getAllClubs();
        return ResponseEntity.ok().body("동아리 가져오기 성공");
    }

    @PostMapping("/write-post")
    public ResponseEntity<String> writePost(@RequestBody BoardWriteRequest dto) {
        boardService.writePost(dto.getTitle(), dto.getContent(), dto.getPhoto());
        return ResponseEntity.ok().body("글 등록 완료");
    }

    @GetMapping("/read-post")
    public ResponseEntity<String> readPost(){
        return ResponseEntity.ok().body("글 가져오기 성공");
    }
}
