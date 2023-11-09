package com.likelion.dub.oAuth.service;

import com.likelion.dub.oAuth.domain.OAuthInfoResponse;
import com.likelion.dub.oAuth.domain.OAuthLoginParams;
import com.likelion.dub.oAuth.domain.OAuthProvider;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}