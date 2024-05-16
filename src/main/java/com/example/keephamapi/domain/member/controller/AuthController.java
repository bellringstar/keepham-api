package com.example.keephamapi.domain.member.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.member.dto.LoginRequest;
import com.example.keephamapi.domain.member.dto.LoginResponse;
import com.example.keephamapi.domain.member.dto.SignUpRequest;
import com.example.keephamapi.domain.member.dto.SignUpResponse;
import com.example.keephamapi.domain.member.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public Api<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = authService.signUp(request);

        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return Api.OK(response);
    }
}
