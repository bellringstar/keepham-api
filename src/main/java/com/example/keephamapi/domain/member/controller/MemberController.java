package com.example.keephamapi.domain.member.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.member.dto.SignUpRequest;
import com.example.keephamapi.domain.member.dto.SignUpResponse;
import com.example.keephamapi.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Api<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = memberService.signUp(request);

        return Api.OK(response);
    }
}
