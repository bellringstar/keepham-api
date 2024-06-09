package com.example.keephamapi.domain.member.entity;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.BaseTimeEntity;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"username", "authorities", "enabled", "accountNonExpired", "accountNonLocked",
        "credentialsNonExpired"})
public class Member extends BaseTimeEntity implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String tel;

    private String email;

    @Embedded
    private Address address;

    @Builder
    public Member(String loginId, String password, String name, String nickName, String tel, String email,
                  Address address) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.tel = tel;
        this.email = email;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
