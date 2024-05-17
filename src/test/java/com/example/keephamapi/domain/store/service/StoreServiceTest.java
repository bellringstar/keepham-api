package com.example.keephamapi.domain.store.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.store.dto.StoreCreateRequest;
import com.example.keephamapi.domain.store.dto.StoreCreateResponse;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.entity.enums.Category;
import com.example.keephamapi.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createStore_shouldCreateAndReturnStore() {
        // Given
        StoreCreateRequest request = new StoreCreateRequest();
        request.setCategory(Category.KOREA);
        request.setName("Test Store");
        request.setAddress(new Address("Street", "City", "Zip"));
        request.setMinOrderAmount(10000L);
        request.setDeliveryFeeToDisplay(2000L);
        request.setLogoUrl("http://example.com/logo.png");
        request.setThumbnailUrl("http://example.com/thumbnail.png");
        request.setCoordinate(new Coordinate(0.0, 0.0));

        Store store = Store.builder()
                .category(request.getCategory())
                .name(request.getName())
                .address(request.getAddress())
                .minOrderAmount(request.getMinOrderAmount())
                .deliveryFeeToDisplay(request.getDeliveryFeeToDisplay())
                .logoUrl(request.getLogoUrl())
                .thumbnailUrl(request.getThumbnailUrl())
                .coordinate(request.getCoordinate())
                .build();

        when(storeRepository.save(any(Store.class))).thenReturn(store);

        // When
        StoreCreateResponse response = storeService.createStore(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getCategory()).isEqualTo(request.getCategory());
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getAddress()).isEqualTo(request.getAddress());
        assertThat(response.getMinOrderAmount()).isEqualTo(request.getMinOrderAmount());
        assertThat(response.getDeliveryFeeToDisplay()).isEqualTo(request.getDeliveryFeeToDisplay());
        assertThat(response.getLogoUrl()).isEqualTo(request.getLogoUrl());
        assertThat(response.getThumbnailUrl()).isEqualTo(request.getThumbnailUrl());
        assertThat(response.getCoordinate()).isEqualTo(request.getCoordinate());

        verify(storeRepository, times(1)).save(any(Store.class));
    }
}