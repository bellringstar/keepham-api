package com.example.keephamapi.domain.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.entity.enums.Category;
import com.example.keephamapi.domain.store.repository.StoreRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StoreViewServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreViewService storeViewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findStoreById_shouldReturnStoreIfExists() {
        // Given
        Long storeId = 1L;
        Store store = Store.builder()
                .category(Category.KOREA)
                .name("Test Store")
                .address(new Address("Street", "City", "Zip"))
                .minOrderAmount(10000L)
                .deliveryFeeToDisplay(2000L)
                .logoUrl("http://example.com/logo.png")
                .thumbnailUrl("http://example.com/thumbnail.png")
                .coordinate(new Coordinate(0.0, 0.0))
                .build();
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        // When
        Store result = storeViewService.findStoreById(storeId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCategory()).isEqualTo(Category.KOREA);
        assertThat(result.getName()).isEqualTo("Test Store");

        verify(storeRepository, times(1)).findById(storeId);
    }

    @Test
    public void findStoreById_shouldThrowExceptionIfNotFound() {
        // Given
        Long storeId = 1L;
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            storeViewService.findStoreById(storeId);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ApiException.class)
                .hasMessage("존재하지 않은 가게");

        verify(storeRepository, times(1)).findById(storeId);
    }

}