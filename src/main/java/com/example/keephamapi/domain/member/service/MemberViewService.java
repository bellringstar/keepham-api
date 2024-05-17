package com.example.keephamapi.domain.member.service;

import com.example.keephamapi.common.error.member.MemberErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberViewService {

    private final MemberRepository memberRepository;

    public Member findMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(MemberErrorCode.USER_NOT_FOUND));
    }
}
