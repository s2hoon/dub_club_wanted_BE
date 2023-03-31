package com.likelion.dub.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column
    private String image;

    @Column
    @Lob
    private String introduction;

    @Column
    private String clubName;

    @OneToOne(mappedBy = "club")
    private Post post;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "club")
    private List<Tag> tags = new ArrayList<>();

    @OneToOne(mappedBy = "club",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true)
    private ClubImage clubImage;


    public void setMember(Member member) {
        this.member = member;
        member.setClub(this);
    }


}
