package com.example.keephamapi.domain.member.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.entity.enums.MemberRole;
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
public class SignUpRequest {

    private String loginId;

    private String password;

    private String name;

    private String nickName;

    private String tel;

    private String email;

    private Address address;

    private MemberRole role;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .nickName(nickName)
                .tel(tel)
                .email(email)
                .address(address)
                .role(role)
                .build();
    }

}
