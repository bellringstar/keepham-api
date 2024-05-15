package com.example.keephamapi.domain.member;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Api<String> me(@RequestParam String name) {
        try {
            if (name.equals("global")) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            throw new ApiException(ErrorCode.SERVER_ERROR, ex);
        }

        return Api.OK(name);
    }
}
