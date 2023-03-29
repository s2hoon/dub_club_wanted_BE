package com.likelion.dub.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostWritingRequest {

    @JsonProperty
    private String clubName;
    @JsonProperty
    private String title;
    @JsonProperty
    private String content;

    @JsonProperty
    private int category;





}
