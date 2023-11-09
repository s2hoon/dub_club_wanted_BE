package com.likelion.dub.member.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ClubMemberJoinRequest {

    @JsonProperty
    private String email;
    @JsonProperty
    private String name;
    @JsonProperty
    private String password;

    @JsonProperty
    private String role;

    @JsonProperty
    private String group;

    @JsonProperty
    private String category;

    @JsonProperty
    @Lob
    private String introduction;


}
