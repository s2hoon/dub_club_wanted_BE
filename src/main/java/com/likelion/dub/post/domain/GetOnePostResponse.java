package com.likelion.dub.post.domain;

import jakarta.persistence.Lob;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetOnePostResponse {


    private String title;
    private String clubName;
    @Lob
    private String content;

    private List<String> comments;

    private String postImage;

    private String form;

}
