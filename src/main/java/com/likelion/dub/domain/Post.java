package com.likelion.dub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name ="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "post")
    private Image image;


    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Column
    private String clubName;
    @Column
    private String title;

    @Column
    @Lob
    private String content;


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