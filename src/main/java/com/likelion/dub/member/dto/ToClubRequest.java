package com.likelion.dub.member.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToClubRequest {

    @JsonProperty
    private String clubName;

    @Lob
    @JsonProperty
    private String introduction;

    @JsonProperty
    private String group;

    @JsonProperty
    private String clubImageUrl;

    @JsonProperty
    private String question1;

    @JsonProperty
    private String question2;

    @JsonProperty
    private String question3;


}
