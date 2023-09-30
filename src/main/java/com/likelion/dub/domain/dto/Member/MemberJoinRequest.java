package com.likelion.dub.domain.dto.Member;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    @JsonProperty
    private String email;
    @JsonProperty
    private String name;
    @JsonProperty
    private String password;
    @JsonProperty
    @Nullable
    private String gender;
    @JsonProperty
    private String role;


}
