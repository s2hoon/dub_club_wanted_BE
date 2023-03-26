package com.likelion.dub.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordRequest {


    @JsonProperty
    private String currentPassword;
    @JsonProperty
    private String newPassword;



}
