package com.likelion.dub.member.infrastructure;

import com.likelion.dub.club.infrastructure.Club;
import com.likelion.dub.common.enumeration.Role;
import com.likelion.dub.member.domain.MemberDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne(mappedBy = "member")
    private Club club;


    @Column(length = 512, nullable = false)
    private String email;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 512, nullable = false)
    private String password;

    @Column(length = 32, nullable = true)
    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member fromDomain(MemberDomain memberDomain) {
        Member member = new Member();
        member.id = memberDomain.getId();
        member.email = memberDomain.getEmail();
        member.name = memberDomain.getName();
        member.gender = memberDomain.getGender();
        member.password = memberDomain.getPassword();
        member.role = memberDomain.getRole();
        return member;
    }


    public MemberDomain toDomain() {
        return MemberDomain.builder()
                .id(id)
                .email(email)
                .name(name)
                .password(password)
                .gender(gender)
                .role(role)
                .build();

    }


}
