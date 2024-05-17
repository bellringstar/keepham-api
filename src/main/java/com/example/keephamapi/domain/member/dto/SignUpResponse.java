package com.example.keephamapi.domain.member.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class SignUpResponse {

    private String loginId;

    private String name;

    private String nickName;

    private String tel;

    private String email;

    private Address address;

    public static SignUpResponse toResponse(Member member) {
        return SignUpResponse.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickName(member.getNickName())
                .tel(member.getTel())
                .email(member.getEmail())
                .address(member.getAddress())
                .build();
    }

}
