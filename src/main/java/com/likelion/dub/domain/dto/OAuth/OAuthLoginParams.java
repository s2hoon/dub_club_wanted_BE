package com.likelion.dub.domain.dto.OAuth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
