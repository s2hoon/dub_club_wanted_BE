package com.likelion.dub.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostWritingRequest {


    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String content;


}
