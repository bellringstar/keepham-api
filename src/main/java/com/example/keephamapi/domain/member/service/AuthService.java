package com.example.keephamapi.domain.member.service;

import com.example.keephamapi.common.error.member.MemberErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.member.dto.LoginRequest;
import com.example.keephamapi.domain.member.dto.LoginResponse;
import com.example.keephamapi.domain.member.dto.SignUpRequest;
import com.example.keephamapi.domain.member.dto.SignUpResponse;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.repository.MemberRepository;
import com.example.keephamapi.security.jwt.JwtTokenProvider;
import com.example.keephamapi.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider tokenProvider;

    public SignUpResponse signUp(SignUpRequest request) {

        if (memberRepository.existsByLoginId(request.getLoginId()) || memberRepository.existsByName(request.getName())) {
            throw new ApiException(MemberErrorCode.DUPLICATE);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = request.toEntity();
        memberRepository.save(member);

        return SignUpResponse.toResponse(member);
    }

    public LoginResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());

        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        TokenDto token = tokenProvider.generateToken(authentication.getName());

        return new LoginResponse(token.getAccessToken(), token.getRefreshToken());
    }
}
