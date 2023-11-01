package com.likelion.dub.token.service;

import com.likelion.dub.token.domain.OAuthInfoResponse;
import com.likelion.dub.token.domain.OAuthLoginParams;
import com.likelion.dub.token.domain.OAuthProvider;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}