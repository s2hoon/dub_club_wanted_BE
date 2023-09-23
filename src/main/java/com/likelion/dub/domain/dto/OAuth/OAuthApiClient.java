package com.likelion.dub.domain.dto.OAuth;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}