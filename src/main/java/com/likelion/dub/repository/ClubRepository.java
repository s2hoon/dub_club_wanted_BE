package com.likelion.dub.repository;

import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByClubName(String clubName);

}
