package com.likelion.dub.dto.Member;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
    private String formUrl;
    

}
