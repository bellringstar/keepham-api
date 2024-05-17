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
public class StoreCreateRequest {

    private Category category;

    private String name;

    private Address address;

    private Long minOrderAmount;

    private Long deliveryFeeToDisplay;

    private String logoUrl;

    private String thumbnailUrl;

    @Embedded
    private Coordinate coordinate;

}
