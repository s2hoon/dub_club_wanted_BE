package com.likelion.dub.service;

import com.likelion.dub.domain.Board;
import com.likelion.dub.domain.dto.BoardGetRequest;
import com.likelion.dub.domain.dto.BoardWritingRequest;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
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
    public List<BoardGetRequest> getAllClubs() {
        return boardRepository.getAllClubs();
    }
    public void writePost(String clubName,String title, String content, Object photo){
        boardRepository.findByClubName(clubName)
                .ifPresent(board -> {
                    throw new AppException(Errorcode.CLUB_EXIST, "이미 작성하신 글이 있습니다.");
                });

        Board board = Board.builder()
                .clubName(clubName)
                .title(title)
                .content(content)
                .photo(photo)
                .build();

        boardRepository.save(board);
    }
}
