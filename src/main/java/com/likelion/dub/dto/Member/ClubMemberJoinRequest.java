package com.likelion.dub.dto.Member;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
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
    @Nullable
    private String gender;
    @JsonProperty
    private String role;

    @JsonProperty
    private String groupName;

    @JsonProperty
    private String category;

    @JsonProperty
    @Lob
    private String introduction;


}
