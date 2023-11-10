package com.likelion.dub.club.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTagRequest {

    @JsonProperty
    private String tagName;


}
