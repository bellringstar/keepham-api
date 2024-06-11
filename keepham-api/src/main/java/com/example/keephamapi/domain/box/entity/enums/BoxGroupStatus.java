package com.example.keephamapi.domain.box.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoxGroupStatus {

    ENROLL("등록"),
    AVAILABLE("이용 가능"),
    UNAVAILABLE("고장"),
    DELETED("제거된 그룹");

    private final String description;
}
