package com.example.keephamapi.domain.box.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.entity.enums.BoxStatus;
import com.example.keephamapi.domain.box.repository.BoxRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BoxViewServiceTest {
    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private BoxViewService boxViewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAvailableBoxById_shouldReturnBoxIfAvailable() {
        // Given
        Long boxId = 1L;
        Box box = Box.builder()
                .status(BoxStatus.AVAILABLE)
                .address(new Address("Street", "City", "Zip"))
                .coordinate(new Coordinate(0.0, 0.0))
                .password("password")
                .build();
        when(boxRepository.findById(boxId)).thenReturn(Optional.of(box));

        // When
        Box result = boxViewService.getAvailableBoxById(boxId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(BoxStatus.AVAILABLE);

        verify(boxRepository, times(1)).findById(boxId);
    }

    @Test
    public void getAvailableBoxById_shouldThrowExceptionIfNotAvailable() {
        // Given
        Long boxId = 1L;
        Box box = Box.builder()
                .status(BoxStatus.IN_USE)
                .address(new Address("Street", "City", "Zip"))
                .coordinate(new Coordinate(0.0, 0.0))
                .password("password")
                .build();
        when(boxRepository.findById(boxId)).thenReturn(Optional.of(box));

        // When
        ApiException exception = catchThrowableOfType(() -> {
            boxViewService.getAvailableBoxById(boxId);
        }, ApiException.class);

        // Then
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCodeIfs()).isEqualTo(ErrorCode.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("사용할 수 없는 박스");

        verify(boxRepository, times(1)).findById(boxId);
    }

    @Test
    public void getAvailableBoxById_shouldThrowExceptionIfBoxNotFound() {
        // Given
        Long boxId = 1L;
        when(boxRepository.findById(boxId)).thenReturn(Optional.empty());

        // When
        ApiException exception = catchThrowableOfType(() -> {
            boxViewService.getAvailableBoxById(boxId);
        }, ApiException.class);

        // Then
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCodeIfs()).isEqualTo(ErrorCode.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 박스");

        verify(boxRepository, times(1)).findById(boxId);
    }
}