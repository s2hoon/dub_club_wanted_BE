package com.likelion.dub.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardWriteRequest {
    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    @JsonProperty
    private Object photo;

}
