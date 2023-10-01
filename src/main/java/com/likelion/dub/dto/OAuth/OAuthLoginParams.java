package com.likelion.dub.dto.OAuth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {

    OAuthProvider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}
