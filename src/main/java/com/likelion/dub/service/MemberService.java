package com.likelion.dub.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.*;

import com.likelion.dub.repository.ClubRepository;
import com.likelion.dub.repository.MemberRepository;

import com.likelion.dub.configuration.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Optional;



@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;



    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; //1시간


    public boolean checkEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return !member.isPresent();
    }



    /**
     * 일반회원 회원가입
     * @param email
     * @param name
     * @param password
     * @param gender
     * @param role
     */
    public void join(String email, String name, String password, String gender, String role) {
        // 중복 이메일 검사
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            throw new BaseException(BaseResponseStatus.EMAIL_ALREADY_EXIST);
        }

        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        member.setPassword(hashedPassword);
        member.setGender(gender);
        member.setRole(role);
        memberRepository.save(member);

    }

    /**
     * 동아리장 회원가입
     * @param email
     * @param name
     * @param password
     * @param gender
     * @param role
     * @param introduction
     * @param groupName
     * @param category
     * @param file
     */
    public void joinClub(String email, String name, String password, String gender, String role, String introduction, String groupName,String category , MultipartFile file)  {

        // 중복 이메일 검사
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            throw new BaseException(BaseResponseStatus.EMAIL_ALREADY_EXIST);
        }
        try {
            // 멤버 저장
            Member member = new Member();
            member.setEmail(email);
            member.setName(name);
            String hashedPassword = bCryptPasswordEncoder.encode(password);
            member.setPassword(hashedPassword);
            member.setGender(gender);
            member.setRole(role);
            memberRepository.save(member);
            // 동아리 저장
            Club club = new Club();
            club.setClubName(name);
            club.setIntroduction(introduction);
            club.setMember(member);
            club.setGroupName(groupName);
            club.setCategory(category);
            if (file != null) {
                // 프로필 사진 S3에 저장
                Long memberId = member.getId();
                String fileName = memberId + "ClubImage";
                ObjectMetadata metadata= new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
                club.setClubImage("https://dubs3.s3.ap-northeast-2.amazonaws.com/" + fileName);
            }
            clubRepository.save(club);
            // 양방향 연관관계설정
            member.setClub(club);

        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.FILE_SAVE_ERROR);
        }

    }

    public String login(String email, String password) throws BaseException {
        //email 중복확인
        Member selectedUser = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));

        //비밀번호 틀림
        if (!bCryptPasswordEncoder.matches(password, selectedUser.getPassword())) {
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
        }

        String token = JwtTokenUtil.createToken(selectedUser.getEmail(),selectedUser.getRole(), key, expireTimeMs);

        return token;
    }


    public void changePassword(Long id, String password) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id " + id));
        member.setPassword(password);
        memberRepository.save(member);
    }

}

