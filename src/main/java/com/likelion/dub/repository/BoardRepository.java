package com.likelion.dub.repository;

import com.likelion.dub.domain.Board;
import com.likelion.dub.domain.dto.BoardGetRequest;
import com.likelion.dub.domain.dto.BoardWritingRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BoardRepository {
    List<BoardGetRequest> getAllClubs();
}
