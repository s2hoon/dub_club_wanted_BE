package com.likelion.dub.member.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/member-repository-test-data.sql")
public class MemberJpaRepositoryTest {


    @Autowired
    private MemberJpaRepository memberJpaRepository;


    @Test
    void findById_로_멤버_찾아오기() {
        //given
        //when
        Optional<Member> member = memberJpaRepository.findById(1L);

        //then
        assertThat(member.isPresent()).isTrue();

    }

    @Test
    void findById_는_데이터가_없으면_Optional_empty_를_반환한다() {
        //given
        //when
        Optional<Member> member = memberJpaRepository.findById(3L);
        //then
        assertThat(member.isEmpty()).isTrue();

    }

    @Test
    void findByEmail_로_멤버_데이터를_찾아올_수_있다() {
        // given
        // when
        Optional<Member> member = memberJpaRepository.findByEmail("suhoon@naver.com");

        // then
        assertThat(member.isPresent()).isTrue();
    }

    @Test
    void findByEmail는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        // when
        Optional<Member> member = memberJpaRepository.findByEmail("fafafafa@naver.com");

        // then
        assertThat(member.isEmpty()).isTrue();
    }


}