package com.example.keephamapi.domain.box.entity;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.box.entity.enums.UnitBoxStatus;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UnitBox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_box_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private UnitBoxStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    private Long boxNumber;

    private String password; //box를 열 수 있는 패스워드
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_group_id")
    private BoxGroup boxGroup; //박스가 속해있는 그룹

    public UnitBox(UnitBoxStatus status, Member member, Long boxNumber, String password, BoxGroup boxGroup) {
        this.status = status;
        this.member = member;
        this.boxNumber = boxNumber;
        this.password = password;
        this.boxGroup = boxGroup;
    }

    public void assignBoxToMember(Member member) {
        this.member = member;
        this.status = UnitBoxStatus.IN_USE;
        this.password = "password"; //TODO: 패스워드 자동 생성 후 리턴 필요.
    }
}
