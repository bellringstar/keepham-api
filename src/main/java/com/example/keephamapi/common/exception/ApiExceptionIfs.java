package com.example.keephamapi.common.exception;

import com.example.keephamapi.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();
}
