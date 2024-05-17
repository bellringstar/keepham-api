package com.example.keephamapi.domain.store.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.entity.enums.Category;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {

    private Long id;

    private Category category;

    private String name;

    private Address address;

    private Long minOrderAmount;

    private Long deliveryFeeToDisplay;

    private String logoUrl;

    private String thumbnailUrl;

    @Embedded
    private Coordinate coordinate;

    public static StoreResponse toResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .category(store.getCategory())
                .address(store.getAddress())
                .minOrderAmount(store.getMinOrderAmount())
                .deliveryFeeToDisplay(store.getDeliveryFeeToDisplay())
                .logoUrl(store.getLogoUrl())
                .thumbnailUrl(store.getThumbnailUrl())
                .coordinate(store.getCoordinate())
                .build();
    }
}
