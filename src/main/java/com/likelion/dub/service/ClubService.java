package com.likelion.dub.service;


import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import com.likelion.dub.repository.ClubRepository;
import com.likelion.dub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    public void uploadForm(String url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
        String clubName = member.getClub().getClubName();
        // ClubName 이 존재하는지 확인
        Club club = clubRepository.findByClubName(clubName).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST));
        club.setForm(url);
        clubRepository.save(club);

    }
}
