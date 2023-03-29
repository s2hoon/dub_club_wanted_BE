package com.likelion.dub.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    @JsonProperty
    private String origFileName;

    @JsonProperty
    private String filePath;

    @JsonProperty
    private Long fileSize;

}
