package com.likelion.dub.domain.dto.Club;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTagRequest {

    @JsonProperty
    private String groupName;

    @JsonProperty
    private String category;


}
