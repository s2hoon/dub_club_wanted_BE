package com.likelion.dub.clubHashTag;


import com.likelion.dub.club.infrastructure.Club;
import com.likelion.dub.club.infrastructure.ClubRepository;
import com.likelion.dub.clubHashTag.domain.ClubHashTag;
import com.likelion.dub.clubHashTag.domain.HashTag;
import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import com.likelion.dub.member.infrastructure.Member;
import com.likelion.dub.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClubHashTagService {


    private final ClubRepository clubRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ClubHashTagJpaRepository clubHashTagJpaRepository;
    private final HashTagJpaResposiotry hashTagJpaResposiotry;


    public void createTag(String tagName, String email) {
        Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
        String clubName = member.getClub().getClubName();
        Club club = clubRepository.findByClubName(clubName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST));
        HashTag hashTag = new HashTag();
        hashTag.setTagName(tagName);
        hashTagJpaResposiotry.save(hashTag);
        ClubHashTag clubHashTag = new ClubHashTag();
        clubHashTag.setHashTag(hashTag);
        clubHashTag.setClub(club);
        clubHashTagJpaRepository.save(clubHashTag);
    }
}
