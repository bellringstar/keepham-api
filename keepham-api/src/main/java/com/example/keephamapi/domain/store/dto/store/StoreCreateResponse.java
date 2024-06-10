package com.example.keephamapi.domain.store.dto.store;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.entity.enums.Category;
import com.example.keephamapi.domain.store.entity.enums.StoreStatus;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class StoreCreateResponse {

    private Category category;

    private StoreStatus storeStatus;

    private String name;

    private Address address;

    @Embedded
    private Coordinate coordinate;

    public static StoreCreateResponse toResponse(Store store) {
        return StoreCreateResponse.builder()
                .category(store.getCategory())
                .storeStatus(store.getStoreStatus())
                .name(store.getName())
                .address(store.getAddress())
                .coordinate(store.getCoordinate())
                .build();
    }
}
