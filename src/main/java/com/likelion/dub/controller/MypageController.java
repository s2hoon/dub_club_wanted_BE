package com.likelion.dub.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.dto.PasswordRequest;
import com.likelion.dub.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;



//    @PutMapping("/password")
//    private BaseResponse<String> changePassword(@RequestBody PasswordRequest passwordRequest) {
//        String currentPassword = passwordRequest.getCurrentPassword();
//        String newPassword = passwordRequest.getNewPassword();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//
//    }

}
