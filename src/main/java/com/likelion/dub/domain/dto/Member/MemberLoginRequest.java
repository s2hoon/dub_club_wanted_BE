package com.likelion.dub.domain.dto.Member;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberLoginRequest {


    @JsonProperty
    private String email;
    @JsonProperty
    private String password;


}
