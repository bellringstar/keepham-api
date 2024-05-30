package com.example.keephamapi.domain.member.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.member.dto.LoginRequest;
import com.example.keephamapi.domain.member.dto.LoginResponse;
import com.example.keephamapi.domain.member.dto.SignUpRequest;
import com.example.keephamapi.domain.member.dto.SignUpResponse;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.service.AuthService;
import com.example.keephamapi.security.jwt.JwtTokenProvider;
import com.example.keephamapi.security.jwt.TokenDto;
import com.example.keephamapi.security.jwt.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public Api<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = authService.signUp(request);

        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        tokenService.storeToken(response.getRefreshToken(), request.getLoginId());

        return Api.OK(response);
    }

    @PostMapping("/refresh")
    public Api<TokenDto> refreshToken(@RequestParam String refreshToken) {

        String userId = tokenService.validateRefreshToken(refreshToken);

        String newAccessToken = tokenProvider.generateToken(userId);
        String newRefreshToken = tokenProvider.generateRefreshToken(userId);

        tokenService.storeToken(newRefreshToken, userId);
        tokenService.deleteRefreshToken(refreshToken);

        return Api.OK(new TokenDto(newAccessToken, newRefreshToken));
    }
}
