package com.example.keephamapi.domain.member.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {


    @GetMapping
    public Api<String> test(Authentication authentication) {

        Member member = (Member) authentication.getPrincipal();
        log.info("member : {}", member.getLoginId());

        return Api.OK("response");
    }

}
