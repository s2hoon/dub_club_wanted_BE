package com.likelion.dub.service;

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
    public void writePost(String clubName,String title, String content){
        postRepository.findByClubName(clubName)
                .ifPresent(post -> {
                    throw new AppException(Errorcode.CLUB_EXIST, "이미 작성하신 글이 있습니다.");
                });

        Post post = Post.builder()
                .clubName(clubName)
                .title(title)
                .content(content)
                .build();

        postRepository.save(post);
    }

    public Post readPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new AppException(Errorcode.ID_DOES_NOT_EXIST, "id에 맞는 글이 없습니다.")
        );

    }
}
