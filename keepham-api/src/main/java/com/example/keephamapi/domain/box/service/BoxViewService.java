package com.example.keephamapi.domain.box.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.box.repository.BoxGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoxViewService {

    private final BoxGroupRepository boxGroupRepository;

    public BoxGroup getAvailableBoxById(Long id) {
        BoxGroup boxGroup =  boxGroupRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 박스"));
        if (boxGroup.getStatus().equals(BoxGroupStatus.AVAILABLE)) {
            return boxGroup;
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "사용할 수 없는 박스");
    }
}
