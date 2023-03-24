package com.likelion.dub.repository;

import com.likelion.dub.domain.Board;
import com.likelion.dub.domain.dto.BoardGetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAll();
    Optional<Board> findByClubName(String clubName);
    Optional<Board> findById(Long id);
}
