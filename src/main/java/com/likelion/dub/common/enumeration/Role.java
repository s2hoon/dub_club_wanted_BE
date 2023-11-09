package com.likelion.dub.common.enumeration;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    CLUB("ROLE_CLUB")


    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}