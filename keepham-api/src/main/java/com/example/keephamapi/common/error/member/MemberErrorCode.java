package com.example.keephamapi.common.error.member;

import com.example.keephamapi.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements ErrorCodeIfs {

    // member error code는 1000번대를 사용하기로 결정
    DUPLICATE(HttpStatus.BAD_REQUEST.value(), 1000, "중복된 사용자가 존재합니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 1001, "존재하지 않는 유저입니다."),
    INVALID(HttpStatus.BAD_REQUEST.value(), 1002, "아이디 혹은 비밀번호가 틀렸습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
