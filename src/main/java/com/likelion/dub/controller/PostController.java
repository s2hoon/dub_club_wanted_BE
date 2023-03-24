package com.likelion.dub.controller;

import com.likelion.dub.domain.Board;
import com.likelion.dub.domain.dto.BoardWritingRequest;
import com.likelion.dub.service.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Board>> getAllClubs(Model model) {

        return ResponseEntity.ok().body(boardService.getAllClubs());

    }

    @PostMapping("/write-post")
    public ResponseEntity<String> writePost(@RequestBody BoardWritingRequest dto) {
        boardService.writePost(dto.getClubName(),dto.getTitle(), dto.getContent());
        return ResponseEntity.ok().body("글 등록 완료");
    }

    @GetMapping("/read-post")
    public ResponseEntity<Board> readPost(@RequestParam(value= "id", required = true) Long id){
        return ResponseEntity.ok().body(boardService.readPost(id));
    }
}
