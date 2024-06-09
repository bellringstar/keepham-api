package com.example.keephamapi.domain.box.service;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.box.dto.BoxGroupCreateRequest;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.box.repository.BoxGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
@Rollback
public class BoxGroupServiceTest {

    @Mock
    private BoxGroupRepository boxGroupRepository;

    @InjectMocks
    private BoxGroupService boxGroupService;

    private BoxGroupCreateRequest validRequest;
    private BoxGroup boxGroup;

    @BeforeEach
    void setUp() {
        validRequest = BoxGroupCreateRequest.builder()
                .status(BoxGroupStatus.AVAILABLE)
                .address(new Address("Sample City", "Sample Street", "12345"))
                .coordinate(new Coordinate(37.5665, 126.9780))
                .build();

        boxGroup = BoxGroup.builder()
                .status(BoxGroupStatus.AVAILABLE)
                .address(new Address("Sample City", "Sample Street", "12345"))
                .coordinate(new Coordinate(37.5665, 126.9780))
                .build();
    }

    @Test
    void createBoxGroup_ShouldReturnSavedBoxGroup() {
        // Given
        when(boxGroupRepository.save(any(BoxGroup.class))).thenReturn(boxGroup);

        // When
        BoxGroup createdBoxGroup = boxGroupService.createBoxGroup(validRequest);

        // Then
        assertThat(createdBoxGroup).isNotNull();
        assertThat(createdBoxGroup.getStatus()).isEqualTo(BoxGroupStatus.AVAILABLE);
        assertThat(createdBoxGroup.getAddress().getCity()).isEqualTo("Sample City");
        verify(boxGroupRepository, times(1)).save(any(BoxGroup.class));
    }

    @Test
    void changeBoxGroupStatus_ShouldChangeStatus() {
        // Given
        Long boxGroupId = 1L;
        BoxGroupStatus newStatus = BoxGroupStatus.UNAVAILABLE;
        when(boxGroupRepository.findById(boxGroupId)).thenReturn(Optional.of(boxGroup));

        // When
        boxGroupService.changeBoxGroupStatus(boxGroupId, newStatus);

        // Then
        assertThat(boxGroup.getStatus()).isEqualTo(newStatus);
        verify(boxGroupRepository, times(1)).findById(boxGroupId);
    }

    @Test
    void changeBoxGroupStatus_WhenBoxGroupNotFound_ShouldThrowException() {
        // Given
        Long boxGroupId = 1L;
        BoxGroupStatus newStatus = BoxGroupStatus.UNAVAILABLE;
        when(boxGroupRepository.findById(boxGroupId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> boxGroupService.changeBoxGroupStatus(boxGroupId, newStatus))
                .isInstanceOf(ApiException.class)
                .hasMessage("존재하지 않는 박스 그룹입니다.");
        verify(boxGroupRepository, times(1)).findById(boxGroupId);
    }
}
