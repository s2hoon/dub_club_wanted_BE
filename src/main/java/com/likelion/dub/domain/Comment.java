package com.likelion.dub.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private Boolean is_public;

    @Column
    private Boolean is_anonymous;

    @Column
    private String username;

    @Column
    @Lob
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;




}
