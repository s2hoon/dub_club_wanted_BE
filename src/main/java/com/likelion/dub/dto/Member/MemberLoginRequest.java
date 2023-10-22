package com.likelion.dub.dto.Member;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class MemberLoginRequest {


    @JsonProperty
    private String email;
    @JsonProperty
    private String password;


}
