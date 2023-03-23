package com.likelion.dub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Errorcode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),

    ;

    private HttpStatus httpStatus;
    private String message;


}
