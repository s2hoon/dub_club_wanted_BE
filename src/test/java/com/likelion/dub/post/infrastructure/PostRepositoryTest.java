package com.likelion.dub.post.infrastructure;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
        @Sql(value = "/sql/post-repository-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;


    @Test
    void findAll() {
        //when
        List<Post> posts = postRepository.findAll();
        // then
        Assertions.assertThat(posts).isNotEmpty();

    }

    @Test
    void findById() {

        //when
        Optional<Post> post = postRepository.findById(1L);
        //then
        Assertions.assertThat(post.get().getId()).isEqualTo(1L);
    }
}