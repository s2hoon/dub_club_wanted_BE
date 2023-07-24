package com.likelion.dub.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostWritingRequest {


    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String content;



}
