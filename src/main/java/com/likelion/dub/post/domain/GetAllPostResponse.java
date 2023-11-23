package com.likelion.dub.post.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetAllPostResponse {

    private Long id;
    private String title;
    private String clubName;

    private String clubImage;


}
