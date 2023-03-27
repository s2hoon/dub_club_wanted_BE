package com.likelion.dub.exception;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> baseExceptionHandler(BaseException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(e.getStatus() + " "+e.getMessage());

    }

}
