package com.example.keephamapi.common.exceptionhandler;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.error.member.MemberErrorCode;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
            Exception exception
    ) {
        log.error("", exception);
        return ResponseEntity
                .status(ErrorCode.SERVER_ERROR.getHttpStatusCode())
                .body(
                        Api.ERROR(ErrorCode.SERVER_ERROR, exception.getLocalizedMessage())
                );
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Api<Object>> usernameNotFoundException(
            Exception exception
    ) {
        log.error("", exception);

        return ResponseEntity
                .status(MemberErrorCode.USER_NOT_FOUND.getHttpStatusCode())
                .body(
                        Api.ERROR(MemberErrorCode.USER_NOT_FOUND, exception.getLocalizedMessage())
                );
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Api<Object>> accessDeniedException(
            Exception exception
    ) {
        log.error("", exception);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        Api.ERROR(ErrorCode.BAD_REQUEST, exception.getLocalizedMessage())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        Api.ERROR(ErrorCode.BAD_REQUEST, errors)
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Api<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        Api.ERROR(ErrorCode.BAD_REQUEST, errors)
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Api<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        Api.ERROR(ErrorCode.BAD_REQUEST, ex.getMessage())
                );
    }
}