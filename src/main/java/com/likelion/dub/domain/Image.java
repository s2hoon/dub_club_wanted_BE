package com.likelion.dub.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "file")
public class Image {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    private Post post;

    @Column
    private String origFileName;

    @Column
    private String filePath;
    public Image(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setPost(Post post){
        this.post = post;

        if(!post.getImage().contains(this))
            post.getImage().add(this);
    }

    private Long fileSize;

}
