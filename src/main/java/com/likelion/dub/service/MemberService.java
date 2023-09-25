package com.likelion.dub.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.configuration.JwtTokenUtil;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.dto.GetMemberInfoResponse;
import com.likelion.dub.domain.dto.OAuth.OAuthInfoResponse;
import com.likelion.dub.domain.dto.OAuth.OAuthLoginParams;
import com.likelion.dub.repository.ClubRepository;
import com.likelion.dub.repository.MemberRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AmazonS3Client amazonS3Client;

    private final RequestOAuthInfoService requestOAuthInfoService;

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
                String fileName = name + "_" + "ClubImage";
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
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));

        //비밀번호 틀림
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
        }

        String token = JwtTokenUtil.createToken(member.getEmail(),member.getRole(),member.getName(), key, expireTimeMs);

        return token;
    }



    public String loginKakao(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        Member member = memberRepository.findById(memberId).orElseThrow();
        return JwtTokenUtil.createToken(member.getEmail(),member.getRole(),member.getName(), key, expireTimeMs);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = new Member();
        member.setEmail(oAuthInfoResponse.getEmail());
        member.setName(oAuthInfoResponse.getNickname());
        return memberRepository.save(member).getId();
    }



    public GetMemberInfoResponse getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        GetMemberInfoResponse getMemberInfoResponse = new GetMemberInfoResponse();
        getMemberInfoResponse.setName(member.getName());
        getMemberInfoResponse.setGender(member.getGender());
        getMemberInfoResponse.setRole(member.getRole());
        getMemberInfoResponse.setEmail(member.getEmail());
        getMemberInfoResponse.setClub(member.getClub());
        return getMemberInfoResponse;
    }


    public void changePassword(String currentPassword,String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        if (bCryptPasswordEncoder.matches(currentPassword, member.getPassword())) {
            // 비밀번호가 일치할 때 처리
            String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
            member.setPassword(hashedPassword);
            memberRepository.save(member);
        } else {
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
        }
    }

}

