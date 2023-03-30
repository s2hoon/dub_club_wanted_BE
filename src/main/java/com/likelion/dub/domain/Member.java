package com.likelion.dub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //어플리케이션에서는 기본키 값을 미리 알수 없음, 엔티티를 저장하고 나서야 키 값 확인 기능
    @Column(name = "member_id")
    private Long id;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Long stunum;
    @Column
    private String role;

    @OneToOne(mappedBy = "member")
    private Club club;



}
