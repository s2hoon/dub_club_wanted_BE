package com.likelion.dub.clubHashTag;

import com.likelion.dub.clubHashTag.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagJpaResposiotry extends JpaRepository<HashTag, Long> {
}
