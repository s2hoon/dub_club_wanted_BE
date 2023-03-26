package com.likelion.dub.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Post;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllClubs() {
        return this.postRepository.findAll();
    }
    public BaseResponse<String> writePost(String clubName,String title, String content) throws BaseException {
        postRepository.findByClubName(clubName)
                .ifPresent(post -> {
                    try {
                        throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
                    } catch (BaseException e) {
                        throw new RuntimeException(e);
                    }
                });

        Post post = Post.builder()
                .clubName(clubName)
                .title(title)
                .content(content)
                .build();

        postRepository.save(post);
        return new BaseResponse<>("글 작성 성공");
    }

    public Post readPost(Long id) throws BaseException{
        return postRepository.findById(id)
                .orElseThrow(() -> {
                    try {
                        throw new BaseException(BaseResponseStatus.NOT_EXISTS_POST);
                    } catch (BaseException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }
}
