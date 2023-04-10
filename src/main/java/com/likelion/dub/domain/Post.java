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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String clubName;
    @Column
    private String title;

    @Column
    @Lob
    private String content;


    @OneToOne(mappedBy="post")
    @JoinColumn(name = "club_id")
    private Club club;




    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> image = new ArrayList<>();

    @Column
    private int category;


    public void addImage(Image image){
        this.image.add(image);

        if(image.getPost() != this)
            image.setPost(this);
    }

    public static class PostBuilder {
        private List<Image> image = new ArrayList<>();

        public PostBuilder addImage(Image image) {
            this.image.add(image);
            return this;
        }


    }
}