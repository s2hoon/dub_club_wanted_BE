package com.likelion.dub.member.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.likelion.dub.club.infrastructure.Club;
import com.likelion.dub.club.infrastructure.ClubRepository;
import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import com.likelion.dub.common.enumeration.Role;
import com.likelion.dub.common.util.JwtTokenUtil;
import com.likelion.dub.member.dto.GetMemberInfoResponse;
import com.likelion.dub.member.dto.MemberJoinRequest;
import com.likelion.dub.member.dto.ToClubRequest;
import com.likelion.dub.member.infrastructure.Member;
import com.likelion.dub.oAuth.domain.OAuthInfoResponse;
import com.likelion.dub.oAuth.domain.OAuthLoginParams;
import com.likelion.dub.oAuth.service.RequestOAuthInfoService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        if (member.isPresent()) {
            throw new BaseException(BaseResponseStatus.EMAIL_ALREADY_EXIST);
        }
        return true;
    }

    public String join(MemberJoinRequest memberJoinRequest) {
        // 중복 이메일 검사
        Optional<Member> existingMember = memberRepository.findByEmail(memberJoinRequest.getEmail());
        if (existingMember.isPresent()) {
            throw new BaseException(BaseResponseStatus.EMAIL_ALREADY_EXIST);
        }

        Member member = new Member();
        member.setEmail(memberJoinRequest.getEmail());
        member.setName(memberJoinRequest.getName());
        String hashedPassword = bCryptPasswordEncoder.encode(memberJoinRequest.getPassword());
        member.setPassword(hashedPassword);
        member.setGender(memberJoinRequest.getGender());
        member.setRole(Role.USER.getRoleName());
        memberRepository.save(member);
        return member.getName();
    }

    public String transferToClub(String email, ToClubRequest toClubRequest) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        Club club = new Club();
        club.setClubName(toClubRequest.getClubName());
        club.setIntroduction(toClubRequest.getIntroduction());
        club.setGroupName(toClubRequest.getGroup());
        club.setClubImageUrl(toClubRequest.getClubImageUrl());
        club.setQuestion1("지원동기??");
        club.setMember(member);
        member.setClub(club); //변경감지
        member.setRole("CLUB"); //변경감지
        clubRepository.save(club);
        return club.getClubName();
    }


    public String login(String email, String password) throws BaseException {
        //email 중복확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));

        //비밀번호 틀림
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
        }

        String token = JwtTokenUtil.createToken(member.getEmail(), member.getRole(),
                member.getName(), key, expireTimeMs);

        return token;
    }


    public String loginKakao(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        Member member = memberRepository.findById(memberId).orElseThrow();
        return JwtTokenUtil.createToken(member.getEmail(), member.getRole(), member.getName(), key,
                expireTimeMs);
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
        member.setRole("USER");
        return memberRepository.save(member).getId();
    }


    public GetMemberInfoResponse getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        GetMemberInfoResponse getMemberInfoResponse = new GetMemberInfoResponse();
        getMemberInfoResponse.setName(member.getName());
        getMemberInfoResponse.setGender(member.getGender());
        getMemberInfoResponse.setRole(member.getRole());
        getMemberInfoResponse.setEmail(member.getEmail());
        return getMemberInfoResponse;
    }


    public void changePassword(String email, String currentPassword, String newPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        if (bCryptPasswordEncoder.matches(currentPassword, member.getPassword())) {
            // 비밀번호가 일치할 때 처리
            String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
            member.setPassword(hashedPassword);
            //service 단 @Transactional 로 인해 save 호출이 필요없음. 즉, flush() 가 자동으로됨
            //memberRepository.save(member);
        } else {
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
        }
    }


}

