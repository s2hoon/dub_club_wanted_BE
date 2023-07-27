package com.likelion.dub.service;

import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;
import com.likelion.dub.repository.MemberRepository;
import com.likelion.dub.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired MemberRepository memberRepository;


    @Autowired MemberService memberService;

    @Autowired PostRepository postRepository;

    @Autowired PostService postService;


    @BeforeEach
    void createClub(){
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content.".getBytes());
        memberService.joinClub("suhoon@naver.com", "name", "password", "gender", "CLUB","introduction","groupName","category",file);
    }

    @Test
    @WithMockUser(username = "suhoon@naver.com",roles="CLUB")
    void testWritePost() throws BaseException, IOException {
        //given

        Member member = new Member(1L, null, null, "suhoon@naver.com", "name", "password", "gender", "CLUB");
        Club club = new Club(1L, null, "name", "introduction", "groupName", "category", "clubImage", member);
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content.".getBytes());
        //when
        postService.writePost("title", "content", file);

        //then





    }
}