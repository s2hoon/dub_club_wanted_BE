package com.likelion.dub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Errorcode {
    SUCCESS(HttpStatus.OK, 1000, "OK"),
    USERNAME_DUPLICATED(HttpStatus.CONFLICT,2000, "중복된 이름이 있습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, 2001,"패스워드가 맞지 않습니다."),
    CLUB_EXIST(HttpStatus.CONFLICT, 3000,"이미 작성한 글이 있습니다." ),
    ID_DOES_NOT_EXIST(HttpStatus.CONFLICT, 3001,"글이 존재하지 않습니다.")
    ;

    private HttpStatus httpStatus;
    private Integer code;
    private String message;


}
