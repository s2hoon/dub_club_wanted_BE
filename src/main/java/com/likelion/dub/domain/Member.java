package com.likelion.dub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //어플리케이션에서는 기본키 값을 미리 알수 없음, 엔티티를 저장하고 나서야 키 값 확인 기능
    @Column(name = "member_id")
    private Long id;

    @OneToOne(mappedBy = "member")
    private Club club;



    @OneToMany(mappedBy = "member")
    private List<Post> post = new ArrayList<>();

    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;

    @Column
    private String gender;

    @Column
    private String role;



    public void setClub(Club club) {
        this.club = club;
    }


}
