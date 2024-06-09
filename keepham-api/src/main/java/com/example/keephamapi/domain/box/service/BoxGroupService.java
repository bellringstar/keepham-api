package com.example.keephamapi.domain.box.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.box.dto.BoxGroupCreateRequest;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.box.repository.BoxGroupRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
//TODO: 모든 dto 유효성 검사 추가해야함
public class BoxGroupService {

    private final BoxGroupRepository boxGroupRepository;

    public BoxGroup createBoxGroup(BoxGroupCreateRequest request) {

        BoxGroup boxGroup = BoxGroup.builder()
                .status(request.getStatus())
                .address(request.getAddress())
                .coordinate(request.getCoordinate())
                .build();

        BoxGroup savedBoxGroup = boxGroupRepository.save(boxGroup);
        return savedBoxGroup;
    }

    public void changeBoxGroupStatus(Long boxGroupId, BoxGroupStatus status) {

        Optional<BoxGroup> boxGroup = boxGroupRepository.findById(boxGroupId);

        if (boxGroup.isEmpty()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 박스 그룹입니다.");
        }

        boxGroup.get().changeBoxGroupStatus(status);
    }
}
