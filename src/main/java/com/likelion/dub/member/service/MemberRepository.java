package com.likelion.dub.member.service;

import com.likelion.dub.member.infrastructure.Member;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByEmail(String email);

    Member save(Member member);
    
    Optional<Member> findById(Long memberId);

}
