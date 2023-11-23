package com.likelion.dub.member.service;

import com.likelion.dub.member.domain.MemberDomain;
import java.util.Optional;

public interface MemberRepository {

    Optional<MemberDomain> findByEmail(String email);


    Optional<MemberDomain> findById(Long memberId);

    MemberDomain save(MemberDomain memberDomain);

}
