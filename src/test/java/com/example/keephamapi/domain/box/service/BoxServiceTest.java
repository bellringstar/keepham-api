package com.example.keephamapi.domain.box.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.dto.CreateBoxRequest;
import com.example.keephamapi.domain.box.dto.CreateBoxResponse;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.entity.enums.BoxStatus;
import com.example.keephamapi.domain.box.repository.BoxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BoxServiceTest {

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private BoxService boxService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBox() {
        //given
        CreateBoxRequest request = new CreateBoxRequest();
        request.setStatus(BoxStatus.AVAILABLE);
        request.setAddress(new Address("Street", "City", "Zip"));
        request.setCoordinate(new Coordinate(0.0, 0.0));
        request.setPassword("password");

        Box box = Box.builder()
                .status(request.getStatus())
                .address(request.getAddress())
                .coordinate(request.getCoordinate())
                .password(request.getPassword())
                .build();

        when(boxRepository.save(any(Box.class))).thenReturn(box);
        //when
        CreateBoxResponse response = boxService.createBox(request);

        //then
        assertThat(request.getStatus()).isEqualTo(response.getStatus());
        assertThat(request.getAddress()).isEqualTo(response.getAddress());
        assertThat(request.getCoordinate()).isEqualTo(response.getCoordinate());
        assertThat(request.getPassword()).isEqualTo(response.getPassword());

        verify(boxRepository, times(1)).save(any(Box.class));
    }
}