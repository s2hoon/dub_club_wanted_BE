package com.likelion.dub.domain.dto.Member;


import com.likelion.dub.domain.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetMemberInfoResponse {

    private String email;
    private String name;

    private String gender;
    private String role;

    private Club club;


}
