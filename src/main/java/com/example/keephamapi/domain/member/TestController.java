package com.example.keephamapi.domain.member;

import com.example.keephamapi.common.api.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Api<String> me(@RequestParam String name) {

        Integer.parseInt(name);
        return Api.OK(name);
    }
}
