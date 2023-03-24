package com.likelion.dub.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;


    @Column
    private String tag_name;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
