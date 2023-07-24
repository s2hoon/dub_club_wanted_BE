package com.likelion.dub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name ="post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Column
    private String clubName;
    @Column
    private String title;

    @Column
    @Lob
    private String content;

    @Column
    private String postImage;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setClub(Club club) {
        this.club = club;

    }


}