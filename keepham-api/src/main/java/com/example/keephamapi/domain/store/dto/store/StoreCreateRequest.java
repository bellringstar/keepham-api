package com.example.keephamapi.domain.store.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.entity.enums.Category;
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
public class StoreCreateRequest {

    private Category category;

    private String name;

    private Address address;

    private Long minOrderPrice;

    private String logoUrl;

    private String thumbnailUrl;

    private Coordinate coordinate;

    public static Store toEntity(StoreCreateRequest request) {
        return Store.builder()
                .category(request.getCategory())
                .name(request.getName())
                .address(request.getAddress())
                .minOrderPrice(request.getMinOrderPrice())
                .logoUrl(request.getLogoUrl())
                .thumbnailUrl(request.getThumbnailUrl())
                .coordinate(request.getCoordinate())
                .build();
    }
}
