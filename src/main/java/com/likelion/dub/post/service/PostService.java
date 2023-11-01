package com.likelion.dub.post.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.dub.club.infrastructure.Club;
import com.likelion.dub.common.baseResponse.BaseException;
import com.likelion.dub.common.baseResponse.BaseResponseStatus;
import com.likelion.dub.member.infrastructure.Member;
import com.likelion.dub.member.infrastructure.MemberRepository;
import com.likelion.dub.post.domain.GetAllPostResponse;
import com.likelion.dub.post.domain.GetOnePostResponse;
import com.likelion.dub.post.infrastructure.Post;
import com.likelion.dub.post.infrastructure.PostRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public List<GetAllPostResponse> getAllPost() {

        List<Post> allPosts = postRepository.findAll();
        // stream 사용
        List<GetAllPostResponse> getAllPostResponses = allPosts.stream()
                .map(post -> new GetAllPostResponse(post.getId(), post.getPostTitle(), post.getClub().getClubName(),
                        post.getClub().getClubImageUrl()))
                .collect(Collectors.toList());
        return getAllPostResponses;
    }


    public void writing(String email, String title, String content, MultipartFile image) throws BaseException {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        Club club = member.getClub();
        if (club == null) {
            throw new BaseException(BaseResponseStatus.NO_SUCH_CLUB_EXIST);
        }
        String clubName = club.getClubName();
        // post 객체 생성 및 db 에 저장
        Post post = new Post();
        post.setClub(club);
        post.setPostTitle(title);
        post.setContent(content);
        try {
            if (image != null) {
                String fileName = clubName + "_" + "PostImage";
                // 포스터 사진 S3에 저장
                uploadPostImageToS3(fileName, image);
                post.setPostImage("https://dubs3.s3.ap-northeast-2.amazonaws.com/" + fileName);
            }
            postRepository.save(post);

        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.FILE_SAVE_ERROR);
        }

    }


    private void uploadPostImageToS3(String fileName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
    }


    public GetOnePostResponse readPost(Long id) throws BaseException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_POST));
        GetOnePostResponse getOnePostResponse = new GetOnePostResponse();
        getOnePostResponse.setClubName(post.getClub().getClubName());
        getOnePostResponse.setTitle(post.getPostTitle());
        getOnePostResponse.setContent(post.getContent());
        getOnePostResponse.setPostImage(post.getPostImage());
        return getOnePostResponse;
    }


    public void deletePost(Long id) throws BaseException {
        // 저장 되어있는 post 가져오기
        Post post = postRepository.findById(id).orElseThrow(() ->
                new BaseException(BaseResponseStatus.NOT_EXISTS_POST));

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //jwt token 오류
        if (authentication == null || !authentication.isAuthenticated()) {
        }
        // 로그인 된 이메일
        String email = authentication.getName();
        // 로그인 된 멤버
        Member member = memberRepository.findByEmail(email).orElseThrow();
        // 로그인 된 클럽
        String clubName = member.getClub().getClubName();


    }


}




