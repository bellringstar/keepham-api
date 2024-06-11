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
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "상태는 필수 항목입니다.")
    private BoxGroupStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boxGroup")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Embedded
    @NotNull(message = "주소는 필수 항목입니다.")
    private Address address;

    @Embedded
    @NotNull(message = "좌표는 필수 항목입니다.")
    private Coordinate coordinate;

    @OneToMany(mappedBy = "boxGroup")
    private List<UnitBox> boxes = new ArrayList<>(); //해당 박스에 할당된 작은 박스들

    @Builder
    public BoxGroup(BoxGroupStatus status, Address address, Coordinate coordinate) {
        this.status = status;
        this.address = address;
        this.coordinate = coordinate;
    }

    public void changeBoxGroupStatus(BoxGroupStatus status) {
        this.status = status;
    }
}
