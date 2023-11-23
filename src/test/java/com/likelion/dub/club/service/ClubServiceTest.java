package com.likelion.dub.club.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/club-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
public class ClubServiceTest {
    @Autowired
    private ClubService clubService;


    @Test
    void uploadForm() {

        //given
        String url = "www.suhoone.com";
        String email = "suhoon@naver.com";
        //when
        String formUrl = clubService.uploadForm(url, email);
        //then
        Assertions.assertThat(formUrl).isEqualTo("www.suhoone.com");
    }

    @Test
    void updateIntroduce() {

        //given
        String email = "suhoon@naver.com";
        String introduce = "안녕하세요 UMC 입니다";
        //when
        String introduce1 = clubService.updateIntroduce(introduce, email);
        //then
        Assertions.assertThat(introduce1).isEqualTo("안녕하세요 UMC 입니다");

    }

    @Test
    void updateClubImage() {

        // given
        String email = "suhoon@naver.com";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg",
                "Some image data".getBytes());

        // when
        String imageUrl = clubService.updateClubImage(mockMultipartFile, email);

        // then
        Assertions.assertThat(imageUrl).isNotEmpty();


    }


}