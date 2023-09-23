package com.likelion.dub.domain.dto.OAuth;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}