package com.example.keephamapi.domain.member.repository;

import com.example.keephamapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByName(String username);

}
