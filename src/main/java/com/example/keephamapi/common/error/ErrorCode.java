package com.example.keephamapi.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

    OK(HttpStatus.OK.value(), HttpStatus.OK.value(), "성공"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에러"),
    ;


    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
