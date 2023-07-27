package com.likelion.dub.domain.dto;

import com.likelion.dub.domain.Comment;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

}
