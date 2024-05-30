package com.example.keephamapi.domain.store.entity;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.store.entity.enums.Category;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    @Embedded
    private Address address;

    private Long minOrderAmount;

    private Long deliveryFeeToDisplay;

    private String logoUrl;

    private String thumbnailUrl;

    @Embedded
    private Coordinate coordinate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "store")
    private ChatRoom chatRoom;

    @Builder
    public Store(Category category, String name, Address address, Long minOrderAmount, Long deliveryFeeToDisplay,
                 String logoUrl, String thumbnailUrl, Coordinate coordinate, ChatRoom chatRoom) {
        this.category = category;
        this.name = name;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.deliveryFeeToDisplay = deliveryFeeToDisplay;
        this.logoUrl = logoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.coordinate = coordinate;
        this.chatRoom = chatRoom;
    }
}
