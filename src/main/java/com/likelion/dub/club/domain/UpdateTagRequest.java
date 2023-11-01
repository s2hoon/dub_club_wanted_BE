package com.likelion.dub.club.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTagRequest {

    @JsonProperty
    private String tagName;


}
