package com.likelion.dub.post.service;

import com.likelion.dub.post.domain.GetAllPostResponse;
import com.likelion.dub.post.domain.GetOnePostResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
public class PostServiceTest {
    @Autowired
    PostService postService;


    @Test
    void getAllPost() {
        //when
        List<GetAllPostResponse> results = postService.getAllPost();
        //then
        Assertions.assertThat(results).isNotEmpty();

    }

    @Test
    void writing() {
        //given
        String email = "suhoon@naver.com";
        String title = "동아리를 지원해주세요";
        String content = "게시글 content";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg",
                "Some image data".getBytes());
        Authentication authentication = new UsernamePasswordAuthenticationToken("suhoon@naver.com", "password");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        //when
        String postTitle = postService.writing(email, title, content, mockMultipartFile);
        //then
        Assertions.assertThat(postTitle).isEqualTo("동아리를 지원해주세요");
    }

    @Test
    void readPost() {

        //when
        GetOnePostResponse result = postService.readPost(1L);
        //then
        Assertions.assertThat(result.getTitle()).isEqualTo("First Post in 멋쟁이사지");
    }

    @Test
    void deletePost() {
    }
}