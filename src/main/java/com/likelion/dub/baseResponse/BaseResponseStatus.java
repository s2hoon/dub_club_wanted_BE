package com.likelion.dub.baseResponse;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : Success
     */
    SUCCESS(true, 1000, "요청에 성공했습니다."),

    /**
     * 2XXX : Common
     */
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    FILE_SAVE_ERROR(false, 2001, "파일 저장에 실패하였습니다."),
    FILE_DELETE_ERROR(false, 2002, "파일 삭제에 실패하였습니다."),

    /**
     * 3XXX : Member
     */
    INVALID_MEMBER_JWT(false, 3000, "권한이 없는 회원의 접근입니다."),
    EMPTY_PROFILE_IMAGE(false, 3001, "프로필 이미지를 입력해주세요."),

    JWT_TOKEN_ERROR(false, 3002, "jwt 토큰을 확인해주세요"),

    USERS_EMPTY_USER_ID(false, 3003, "유저 아이디 값을 확인해주세요."),

    EMAIL_ALREADY_EXIST(false, 3004, "이미 가입된 이메일 주소입니다."),
    WRONG_EMAIL(false, 3005, "잘못된 이메일 주소입니다."),
    WRONG_PASSWORD(false, 3006, "잘못된 비밀번호입니다."),
    STU_NUM_ALREADY_EXIST(false, 3007, "이미 가입된 학번 입니다."),
    NO_SUCH_MEMBER_EXIST(false, 3008, "존재하지 않는 회원입니다."),
    NO_SUCH_CLUB_EXIST(false, 3009, "존재하지 않는 동아리입니다."),

    /**
     * 4XXX : Post
     */
    NOT_EXISTS_POST(false, 4000, "게시물이 존재하지 않습니다."),
    FAILED_GET_POST(false, 4001, "게시물 조회에 실패하였습니다."),
    NOT_EXISTS_TAG_NAME_POST(true, 4002, "해당 태그를 가진 게시물이 없습니다."),
    DELETE_FAIL_POST(false, 4003, "게시물 삭제에 실패하였습니다.");


    /**
     * 5xxx: Mypage
     */


    private final boolean isSuccess;
    private final int code;
    private final String message;


    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}