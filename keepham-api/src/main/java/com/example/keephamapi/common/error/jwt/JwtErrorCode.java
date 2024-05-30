package com.example.keephamapi.common.error.jwt;

import com.example.keephamapi.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum JwtErrorCode implements ErrorCodeIfs {

    //토큰 관련 에러 코드 2000번
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), 2000, "유효하지 않은 토큰"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
