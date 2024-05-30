package com.example.keephamapi.domain.box.service;

import com.example.keephamapi.domain.box.dto.CreateBoxRequest;
import com.example.keephamapi.domain.box.dto.CreateBoxResponse;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.repository.BoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoxService {

    private final BoxRepository boxRepository;

    public CreateBoxResponse createBox(CreateBoxRequest request) {

        Box box = Box.builder()
                .address(request.getAddress())
                .coordinate(request.getCoordinate())
                .status(request.getStatus())
                .coordinate(request.getCoordinate())
                .password(request.getPassword())
                .build();

        boxRepository.save(box);

        return CreateBoxResponse.toResponse(box);
    }
}
