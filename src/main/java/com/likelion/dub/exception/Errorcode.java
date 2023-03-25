package com.likelion.dub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Errorcode {

    //Member
    //이메일 중복 체크
    EMAIL_CHECK_COMPLETE(HttpStatus.OK,1000,"이메일 사용 가능"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, 2000, "중복된 이메일이 있습니다"),

    //학번 중복 체크
    STU_NUM_CHECK_COMPLETE(HttpStatus.OK,1000,"학번 사용 가능"),
    STU_NUM_DUPLICATED(HttpStatus.CONFLICT, 2000, "중복된 학번이 있습니다"),

    //회원가입
    JOIN_COMPLETE(HttpStatus.OK, 1000, "회원 가입 완료"),
    JOIN_FAILED(HttpStatus.OK, 2000, "회원 가입 실패"),

    //로그인
    LOGIN_COMPLETE(HttpStatus.OK, 1000, "로그인 완료"),
    LOGIN_FAILED(HttpStatus.OK, 2000, "로그인 실패"),



    //Post


    //Club

    //Admin

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
