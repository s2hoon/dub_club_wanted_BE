package com.likelion.dub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Errorcode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
    CLUB_EXIST(HttpStatus.CONFLICT,"" ),
    ID_DOES_NOT_EXIST(HttpStatus.CONFLICT, "")
    ;

    private HttpStatus httpStatus;
    private String message;


}
