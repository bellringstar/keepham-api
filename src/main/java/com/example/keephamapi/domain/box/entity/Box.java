package com.example.keephamapi.domain.box.entity;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.enums.BoxStatus;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Box {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoxStatus status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "box")
    private ChatRoom chatRoom;

    @Embedded
    private Address address;

    @Embedded
    private Coordinate coordinate;

    private String password; //box를 열 수 있는 패스워드

    @Builder
    public Box(BoxStatus status, ChatRoom chatRoom, Address address, Coordinate coordinate, String password) {
        this.status = status;
        this.chatRoom = chatRoom;
        this.address = address;
        this.coordinate = coordinate;
        this.password = password;
    }

    public void useBox() {
        this.status = BoxStatus.IN_USE;
    }
}
