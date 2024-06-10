package com.example.keephamapi.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_fee_id")
    private Long id;

    private Long minOrderPrice;
    private Long maxOrderPrice;
    private Long deliveryFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public DeliveryFee(Long minOrderPrice, Long maxOrderPrice, Long deliveryFee, Store store) {
        this.minOrderPrice = minOrderPrice;
        this.maxOrderPrice = maxOrderPrice;
        this.deliveryFee = deliveryFee;
        this.store = store;
    }
}
