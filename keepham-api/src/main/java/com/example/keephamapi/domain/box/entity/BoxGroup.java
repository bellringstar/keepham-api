package com.example.keephamapi.domain.box.entity;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class BoxGroup {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoxGroupStatus status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "box")
    private ChatRoom chatRoom;

    @Embedded
    private Address address;

    @Embedded
    private Coordinate coordinate;

    private String password; //box를 열 수 있는 패스워드

    @OneToMany(mappedBy = "boxGroup")
    private List<UnitBox> boxes = new ArrayList<>(); //해당 박스에 할당된 작은 박스들

    @Builder
    public BoxGroup(BoxGroupStatus status, ChatRoom chatRoom, Address address, Coordinate coordinate, String password) {
        this.status = status;
        this.chatRoom = chatRoom;
        this.address = address;
        this.coordinate = coordinate;
        this.password = password;
    }

    public void useBox() {
        this.status = BoxGroupStatus.IN_USE;
    }
}
