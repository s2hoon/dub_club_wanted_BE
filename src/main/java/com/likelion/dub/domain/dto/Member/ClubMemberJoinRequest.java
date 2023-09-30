package com.likelion.dub.domain.dto.Member;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
