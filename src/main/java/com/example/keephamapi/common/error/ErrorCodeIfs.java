package com.example.keephamapi.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();

    Integer getErrorCode();

    String getDescription();
}
