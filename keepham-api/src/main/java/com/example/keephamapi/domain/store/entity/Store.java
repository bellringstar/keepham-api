package com.example.keephamapi.domain.store.entity;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.store.entity.enums.Category;
import com.example.keephamapi.domain.store.entity.enums.StoreOpenStatus;
import com.example.keephamapi.domain.store.entity.enums.StoreStatus;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"menuCategory", "deliveryFees", "menus", "chatRoom"})
@ToString(exclude = {"menuCategory", "deliveryFees", "menus", "chatRoom"})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @Enumerated(EnumType.STRING)
    private StoreOpenStatus openStatus;

    private String name;

    @Embedded
    private Address address;

    private Long minOrderPrice;

    //TODO: 이미지 파일 업로드 기능, 썸네일 생성 후 url 저장 추가
    private String logoUrl;

    private String thumbnailUrl;

    @Embedded
    private Coordinate coordinate;

    // 테이블에는 없지만 관리를 위한 양방향 연결
    @OneToMany(mappedBy = "store", orphanRemoval = true)
    private List<MenuCategory> menuCategory = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true)
    private List<DeliveryFee> deliveryFees = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "store")
    private BusinessCert businessCert;

    @Builder
    public Store(Category category, StoreStatus storeStatus, String name, Address address, Long minOrderPrice,
                 String logoUrl, String thumbnailUrl, Coordinate coordinate) {
        this.category = category;
        this.storeStatus = storeStatus;
        this.name = name;
        this.address = address;
        this.minOrderPrice = minOrderPrice;
        this.logoUrl = logoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.coordinate = coordinate;
    }

    public void deleteStore() {
        this.storeStatus = StoreStatus.PENDING_DELETE;
    }

}
