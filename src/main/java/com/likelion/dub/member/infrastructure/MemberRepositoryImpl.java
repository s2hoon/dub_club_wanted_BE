package com.likelion.dub.member.infrastructure;


import com.likelion.dub.member.domain.MemberDomain;
import com.likelion.dub.member.service.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<MemberDomain> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email).map(Member::toDomain);
    }

    @Override
    public MemberDomain save(MemberDomain memberDomain) {
        return memberJpaRepository.save(Member.fromDomain(memberDomain)).toDomain();
    }

    @Override
    public Optional<MemberDomain> findById(Long memberId) {
        return memberJpaRepository.findById(memberId).map(Member::toDomain);
    }
}



