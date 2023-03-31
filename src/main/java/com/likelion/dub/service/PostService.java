package com.likelion.dub.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Image;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.dto.PostEditRequest;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.ImageRepository;
import com.likelion.dub.repository.MemberRepository;
import com.likelion.dub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final FileHandler fileHandler;


    public List<Post> getAllClubs() {
        return this.postRepository.findAll();
    }
    public BaseResponse<String> writePost(String clubName,String title, String content,int category, List<MultipartFile> files) throws BaseException {
        //이 club 이 없으면 작성 불가
        postRepository.findByClubName(clubName)
                .ifPresent(post -> {
                        throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
                });

        List<Image> imageList = fileHandler.parseFileInfo(files);
        Post.PostBuilder postBuilder = Post.builder()
                .clubName(clubName)
                .title(title)
                .content(content)
                .category(category);

        for (Image image : imageList) {
            postBuilder.addImage(image);
        }

        Post post = postBuilder.build();


        //파일이 존재할 때만 처리
            if(!imageList.isEmpty()){
                for(Image image : imageList){
                    imageRepository.save(image);
                    post.addImage(image);
                }
            }
            postRepository.save(post);
        return new BaseResponse<>("글 작성 성공");
    }

    public Post readPost(Long id) throws BaseException{
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new BaseException(BaseResponseStatus.NOT_EXISTS_POST)

        );

    }


    public void deletePost(Long id) throws BaseException {
        postRepository.findById(id);

    }

    public void editPost(String email, String newTitle, String newContent, int newCategory) throws BaseException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        String clubName = member.getClub().getClubName();
        Post post = postRepository.findByClubName(clubName)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.FAILED_GET_POST));
        post.setTitle(newTitle);
        post.setContent(newContent);
        post.setCategory(newCategory);
        postRepository.save(post);

    }


//    public Member loadMemberByEmail(String email) throws BaseException {
//        Member selectedUser = postRepository.findByEmail(email)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
//
//        return selectedUser;
//
//    }

}
