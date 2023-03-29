package com.likelion.dub.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyPageResponse {
    @JsonProperty
    private String email;
    @JsonProperty
    private String username;
    @JsonProperty
    private Long stunum;
    @JsonProperty
    private String role;


}
