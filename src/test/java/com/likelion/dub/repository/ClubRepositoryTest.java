package com.likelion.dub.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/member-repository-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/club-repository-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class ClubRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;


    @Test
    public void findByClubName_으로_동아리를_가져올수있다() {
        //given
        //when
        Optional<Club> club = clubRepository.findByClubName("멋쟁이사자");

        //then
        assertThat(club.isPresent()).isTrue();
    }

    @Test
    public void findByClubName_으로_동아리장을_가져올수_있다() {
        //given
        //when
        Optional<Club> club = clubRepository.findByClubName("멋쟁이사자");
        Optional<Member> member = Optional.ofNullable(club.get().getMember());
        //then
        assertThat(member.isPresent()).isTrue();
    }


}