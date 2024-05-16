package com.example.keephamapi.domain.member.dto;

import com.example.keephamapi.domain.member.entity.Address;
import com.example.keephamapi.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String loginId;
    private String password;
}
