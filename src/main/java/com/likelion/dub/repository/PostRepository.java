package com.likelion.dub.repository;


import com.likelion.dub.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.club")
    List<Post> findAll();

    Optional<Post> findById(Long id);


}
