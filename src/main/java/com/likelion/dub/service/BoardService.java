package com.likelion.dub.service;

import com.likelion.dub.domain.Board;
import com.likelion.dub.domain.dto.BoardWritingRequest;
import com.likelion.dub.repository.BoardRepository;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public List<BoardWritingRequest> getAllClubs() {
        return boardRepository.getAllClubs();


    }
    public void writePost(String title, String content, Object photo){

    }
}
