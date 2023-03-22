package com.likelion.dub.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{
    private Errorcode errorCode;
    private String message;




}
