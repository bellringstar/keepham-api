package com.example.keephamapi.domain.box.entity;

import com.example.keephamapi.domain.box.entity.enums.UnitBoxStatus;
import com.example.keephamapi.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitBoxTest {

    @Test
    void testAssignBoxToMember() {
        Member member = Member.builder().build(); // assuming a no-arg constructor or builder pattern is available
        UnitBox unitBox = UnitBox.builder()
                .status(UnitBoxStatus.AVAILABLE)
                .boxNumber(1L)
                .build();

        unitBox.assignBoxToMember(member);

        assertThat(unitBox.getMember()).isEqualTo(member);
        assertThat(unitBox.getStatus()).isEqualTo(UnitBoxStatus.IN_USE);
        assertThat(unitBox.getPassword()).hasSize(4);
        assertThat(unitBox.getPassword()).matches("\\d{4}");
    }
}

