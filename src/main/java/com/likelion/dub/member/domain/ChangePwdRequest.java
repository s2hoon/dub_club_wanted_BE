package com.likelion.dub.member.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePwdRequest {


    @JsonProperty
    private String currentPassword;
    @JsonProperty
    private String newPassword;


}
