package com.example.keephamapi.domain.member.service;

import com.example.keephamapi.common.error.member.MemberErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.member.dto.SignUpRequest;
import com.example.keephamapi.domain.member.dto.SignUpResponse;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest request) {

        if (memberRepository.existsByLoginId(request.getLoginId()) || memberRepository.existsByName(request.getName())) {
            throw new ApiException(MemberErrorCode.duplicate);
        }
        log.info("", request.getPassword());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = request.toEntity();
        memberRepository.save(member);

        return SignUpResponse.toResponse(member);
    }
}
