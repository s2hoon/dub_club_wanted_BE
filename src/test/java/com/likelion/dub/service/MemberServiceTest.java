package com.likelion.dub.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/member-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void checkEmail_로_이메일_중복체크를할수있다() {
        //given
        String email = "new_email";
        //when
        boolean checkEmail = memberService.checkEmail(email);
        //then
        assertThat(checkEmail).isTrue();
    }


    @Test
    void join_으로_회원가입을할수있다() {
        //given
        String email = "suhoon@naver.com";
        String name = "조수훈";
        String password = "124";
        String gender = "남자";
        String role = "CLUB";

        //when

        //then
    }

    @Test
    void transferToClub() {
        //given

        //when

        //then
    }

    @Test
    void login() {
        //given

        //when

        //then
    }

    @Test
    void loginKakao() {
        //given

        //when

        //then
    }

    @Test
    void getInfo() {
        //given

        //when

        //then
    }

    @Test
    void changePassword() {
        //given

        //when

        //then
    }
}