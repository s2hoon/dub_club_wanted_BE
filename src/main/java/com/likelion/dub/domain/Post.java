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


    @OneToMany(
            mappedBy = "post",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
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