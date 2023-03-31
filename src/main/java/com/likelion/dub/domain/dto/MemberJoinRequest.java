package com.likelion.dub.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberJoinRequest {
    @JsonProperty
    private String email;
    @JsonProperty
    private String name;
    @JsonProperty
    private String password;
    @JsonProperty
    private Long stunum;
    @JsonProperty
    private String role;




}
