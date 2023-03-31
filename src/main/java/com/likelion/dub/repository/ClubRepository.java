package com.likelion.dub.repository;

import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {


}
