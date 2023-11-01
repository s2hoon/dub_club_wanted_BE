package com.likelion.dub.club.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.dub.club.infrastructure.Club;
import com.likelion.dub.club.infrastructure.ClubRepository;
import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import com.likelion.dub.member.infrastructure.Member;
import com.likelion.dub.member.infrastructure.MemberRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public void uploadForm(String url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
        String clubName = member.getClub().getClubName();
        Club club = clubRepository.findByClubName(clubName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST));

        clubRepository.save(club);

    }

    public void updateIntroduce(String introduction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
        String clubName = member.getClub().getClubName();
        Club club = clubRepository.findByClubName(clubName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST));
        club.setIntroduction(introduction);
        clubRepository.save(club);
    }


    public void updateClubImage(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
        Club club = member.getClub();
        String clubName = club.getClubName();
        String fileName = clubName + "_" + "ClubImage";
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            club.setClubImageUrl("https://dubs3.s3.ap-northeast-2.amazonaws.com/" + fileName);
        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.FILE_SAVE_ERROR);
        }
        clubRepository.save(club);

    }

    public void updateTag(String tagName, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
        Club club = member.getClub();
        club.setGroupName(groupName);
        clubRepository.save(club);
    }

}