package com.likelion.dub.dto.Post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostEditRequest {

    @JsonProperty
    private String title;
    @JsonProperty
    private String content;
    @JsonProperty
    private int category;

}
