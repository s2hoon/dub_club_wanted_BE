package com.likelion.dub.service;

import com.likelion.dub.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void getAllClubs() {

    }
    public void writePost(String title, String content, Object photo){

    }
}
