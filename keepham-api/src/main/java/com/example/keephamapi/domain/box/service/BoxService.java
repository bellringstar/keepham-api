package com.example.keephamapi.domain.box.service;

import com.example.keephamapi.domain.box.dto.CreateBoxRequest;
import com.example.keephamapi.domain.box.dto.CreateBoxResponse;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.repository.BoxGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoxService {

    private final BoxGroupRepository boxGroupRepository;

    public CreateBoxResponse createBox(CreateBoxRequest request) {

        BoxGroup boxGroup = BoxGroup.builder()
                .address(request.getAddress())
                .coordinate(request.getCoordinate())
                .status(request.getStatus())
                .coordinate(request.getCoordinate())
                .password(request.getPassword())
                .build();

        boxGroupRepository.save(boxGroup);

        return CreateBoxResponse.toResponse(boxGroup);
    }
}
