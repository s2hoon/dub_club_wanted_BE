package com.likelion.dub.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;

import com.likelion.dub.domain.dto.GetAllPostResponse;
import com.likelion.dub.domain.dto.GetOnePostResponse;
import com.likelion.dub.repository.MemberRepository;
import com.likelion.dub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public List<GetAllPostResponse> getAllPost() {

        List<Post> allPosts = postRepository.findAll();
        List<GetAllPostResponse> getAllPostResponses =new ArrayList<>();
        for (Post allPost : allPosts) {
            GetAllPostResponse getAllPostResponse = new GetAllPostResponse();
            getAllPostResponse.setId(allPost.getId());
            getAllPostResponse.setTitle(allPost.getTitle());
            getAllPostResponse.setClubName(allPost.getClubName());
            getAllPostResponses.add(getAllPostResponse);
        }
        return getAllPostResponses;
    }


    public BaseResponse<String> writing(String title, String content, MultipartFile file) throws BaseException,IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        Club club = member.getClub();
        if (club == null) {
            throw new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST);
        }
        String clubName = club.getClubName();
        // post 객체 생성 및 db 에 저장
        Post post = new Post();
        post.setClubName(clubName);
        post.setClub(club);
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);
        if (file != null) {
            String fileName = member.getId() + "PostImage";
            // 포스터 사진 S3에 저장
            uploadPostImageToS3(fileName, file);
            post.setPostImage("https://dubs3.s3.ap-northeast-2.amazonaws.com/" + fileName);
        }
        postRepository.save(post);


        return new BaseResponse<>("글 작성 성공");
    }


    public BaseResponse<String> writePost(String title, String content, MultipartFile file) throws BaseException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        Club club = member.getClub();
        if (club == null) {
            throw new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST);
        }
        String clubName = club.getClubName();
        // post 객체 생성 및 db 에 저장
        Post post = new Post();
        post.setClubName(clubName);
        post.setClub(club);
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);
        if (file != null) {
            String fileName = member.getId() + "PostImage";
            // 포스터 사진 S3에 저장
            uploadPostImageToS3(fileName, file);
            post.setPostImage("https://dubs3.s3.ap-northeast-2.amazonaws.com/" + fileName);
        }
        postRepository.save(post);


        return new BaseResponse<>("글 작성 성공");
    }

    private void uploadPostImageToS3(String fileName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
    }


    public GetOnePostResponse readPost(Long id) throws BaseException {
        Optional<Post> post = postRepository.findById(id);
        GetOnePostResponse getOnePostResponse = new GetOnePostResponse();
        getOnePostResponse.setClubName(post.get().getClubName());
        getOnePostResponse.setTitle(post.get().getTitle());
        getOnePostResponse.setContent(post.get().getContent());
        getOnePostResponse.setPostImage(post.get().getPostImage());

        List<String> comments = null;
        getOnePostResponse.setComments(comments);

        return getOnePostResponse;



    }


    public BaseResponse<String> deletePost(Long id) throws BaseException {
        // 저장 되어있는 post 가져오기
        Post post = postRepository.findById(id).orElseThrow(() ->
                new BaseException(BaseResponseStatus.NOT_EXISTS_POST));

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //jwt token 오류
        if (authentication == null || !authentication.isAuthenticated()) {
            return new BaseResponse(BaseResponseStatus.JWT_TOKEN_ERROR);
        }
        // 로그인 된 이메일
        String email = authentication.getName();
        // 로그인 된 멤버
        Member member = memberRepository.findByEmail(email).orElseThrow();
        // 로그인 된 클럽
        String clubName = member.getClub().getClubName();
        // 로그인 된 post
        Post member_post = postRepository.findByClubName(clubName).orElseThrow();


        //post id 가 같은지 검사, 같으면 삭제, 다르면 오류출력
        if (member_post.getId() == id) {
            postRepository.deleteById(id);
            return new BaseResponse<>("삭제 완료");
        } else {
            return new BaseResponse(BaseResponseStatus.INVALID_MEMBER_JWT);
        }
    }


}




