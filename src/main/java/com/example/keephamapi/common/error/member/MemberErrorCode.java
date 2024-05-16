package com.example.keephamapi.common.error.member;

import com.example.keephamapi.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements ErrorCodeIfs {

    // member error code는 1000번대를 사용하기로 결정
    duplicate(HttpStatus.BAD_REQUEST.value(), 1000, "중복된 사용자가 존재합니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
