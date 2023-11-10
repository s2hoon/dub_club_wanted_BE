package com.likelion.dub.member.domain;

import com.likelion.dub.club.infrastructure.Club;
import com.likelion.dub.common.enumeration.Role;
import com.likelion.dub.member.dto.request.MemberJoinRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
@Setter
public class MemberDomain {

    private Long id;

    private Club club;

    private String email;

    private String name;

    private String password;

    private String gender;

    private Role role;

    public static MemberDomain from(MemberJoinRequest memberJoinRequest) {
        return MemberDomain.builder()
                .email(memberJoinRequest.getEmail())
                .name(memberJoinRequest.getName())
                .password(memberJoinRequest.getPassword())
                .gender(memberJoinRequest.getGender())
                .role(Role.ROLE_USER)
                .build();
    }

}
