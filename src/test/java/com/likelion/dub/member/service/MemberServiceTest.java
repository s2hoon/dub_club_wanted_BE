package com.likelion.dub.member.service;

import com.likelion.dub.member.dto.request.MemberJoinRequest;
import com.likelion.dub.member.dto.request.ToClubRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    void checkEmail_로_이메일_중복체크를할수있다() {
        //given
        String email = "new_email";
        //when
        boolean checkEmail = memberService.checkEmail(email);
        //then
        Assertions.assertThat(checkEmail).isTrue();
    }


    @Test
    void join_으로_회원가입을할수있다() {
        //given
        String email = "test@naver.com";
        String name = "조시훈";
        String password = "124";
        String gender = "남자";
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, name, password, gender);
        String encodedPassword = "hased_pasword";
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword);
        //when
        String memberName = memberService.join(memberJoinRequest);
        //then
        Assertions.assertThat(memberName).isEqualTo("조시훈");
    }

    @Test
    void transferToClub_으로동아리전환을할수있다() {
        //given
        String email = "suhoon@naver.com";
        String club_name = "UMC";
        String introduction = "안녕하세요UMC입니다";
        String group_name = "코딩동아리";
        String club_image_url = "naver.com";
        String question1 = "지원동기??";
        ToClubRequest toClubRequest = new ToClubRequest();
        toClubRequest.setClubName(club_name);
        toClubRequest.setIntroduction(introduction);
        toClubRequest.setGroup(group_name);
        toClubRequest.setClubImageUrl(club_image_url);
        toClubRequest.setQuestion1(question1);
        //when
        String clubName = memberService.transferToClub(email, toClubRequest);
        //then
        Assertions.assertThat(clubName).isEqualTo("UMC");

    }

    @Test
    void login_으로_로그인할수가있다() {
        //given
        String email = "test@naver.com";
        String name = "조시훈";
        String password = "124";
        String gender = "남자";
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, name, password, gender);
        String encodedPassword = "hased_pasword";
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword);
        String memberName = memberService.join(memberJoinRequest);
        Mockito.when(bCryptPasswordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        //when
        String token = memberService.login(email, password);

        //then
        Assertions.assertThat(token).isNotEmpty();
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