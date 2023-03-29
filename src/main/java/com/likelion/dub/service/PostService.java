package com.likelion.dub.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Image;
import com.likelion.dub.domain.Post;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.ImageRepository;
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
    private final FileHandler fileHandler;


    public List<Post> getAllClubs() {
        return this.postRepository.findAll();
    }
    public BaseResponse<String> writePost(String clubName, String title, String content, List<MultipartFile> files) throws BaseException {
        postRepository.findByClubName(clubName)
                .ifPresent(post -> {
                        throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);

                });

        Post post = Post.builder()
                .clubName(clubName)
                .title(title)
                .content(content)
                .build();
        List<Image> imageList = fileHandler.parseFileInfo(files);

        //파일이 존재할 때만 처리
            if(!imageList.isEmpty()){
                for(Image image : imageList){
                    post.addImage(imageRepository.save(image));
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
}
