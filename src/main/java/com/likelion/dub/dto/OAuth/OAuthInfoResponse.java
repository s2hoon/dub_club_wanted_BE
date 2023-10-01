package com.likelion.dub.dto.OAuth;

public interface OAuthInfoResponse {

    String getEmail();

    String getNickname();

    OAuthProvider getOAuthProvider();
}