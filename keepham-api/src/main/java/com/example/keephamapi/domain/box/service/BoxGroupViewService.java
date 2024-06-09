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
@Transactional(readOnly = true)
public class BoxGroupViewService {

    private final BoxGroupRepository boxGroupRepository;

    public BoxGroup findAvailableBoxById(Long boxGroupId) {
        return boxGroupRepository.findBoxGroupByIdAndStatus(boxGroupId, BoxGroupStatus.AVAILABLE)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "이용 가능한 박스 그룹이 없습니다."));
    }

    //박스 그룹 전체 검색

    //박스 그룹 조건 검색 -> queryDSL

}
