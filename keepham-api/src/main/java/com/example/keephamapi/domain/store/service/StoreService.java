package com.example.keephamapi.domain.store.service;

import com.example.keephamapi.domain.store.dto.StoreCreateRequest;
import com.example.keephamapi.domain.store.dto.StoreCreateResponse;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreCreateResponse createStore(StoreCreateRequest request) {

        Store store = Store.builder()
                .deliveryFeeToDisplay(request.getDeliveryFeeToDisplay())
                .logoUrl(request.getLogoUrl())
                .minOrderAmount(request.getMinOrderAmount())
                .name(request.getName())
                .thumbnailUrl(request.getThumbnailUrl())
                .coordinate(request.getCoordinate())
                .category(request.getCategory())
                .address(request.getAddress())
                .build();

        storeRepository.save(store);

        return StoreCreateResponse.toResponse(store);
    }
}
