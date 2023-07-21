package com.likelion.dub.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;



    @OneToMany(mappedBy = "club")
    private List<Post> post = new ArrayList<>();

    @Column
    private String clubName;

    @Column
    @Lob
    private String introduction;
    @Column
    private String groupName;
    @Column
    private String category;

    @Lob
    @Column
    private byte[] clubImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setMember(Member member) {
        this.member = member;
        member.setClub(this);
    }



    public void setPost(Post post) {
        this.post.add(post);
        post.setClub(this);
    }

}
