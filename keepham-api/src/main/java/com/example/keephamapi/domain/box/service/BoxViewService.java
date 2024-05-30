package com.example.keephamapi.domain.box.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.box.dto.CreateBoxRequest;
import com.example.keephamapi.domain.box.dto.CreateBoxResponse;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.entity.enums.BoxStatus;
import com.example.keephamapi.domain.box.repository.BoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoxViewService {

    private final BoxRepository boxRepository;

    public Box getAvailableBoxById(Long id) {
        Box box =  boxRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 박스"));
        if (box.getStatus().equals(BoxStatus.AVAILABLE)) {
            return box;
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "사용할 수 없는 박스");
    }
}
