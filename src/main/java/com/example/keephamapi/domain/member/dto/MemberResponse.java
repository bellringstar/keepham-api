package com.example.keephamapi.domain.member.dto;

import com.example.keephamapi.common.entity.Address;
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
public class MemberResponse {

    private Long id;

    private String loginId;

    private String name;

    private String nickName;

    private String tel;

    private String email;

    private Address address;

    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickName(member.getNickName())
                .tel(member.getTel())
                .email(member.getEmail())
                .address(member.getAddress())
                .build();
    }
}
