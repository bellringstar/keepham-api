package com.example.keephamapi.domain.box.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoxGroupStatus {

    AVAILABLE("이용 가능"),
    UNAVAILABLE("고장"),
    IN_USE("사용중"),
    DELETED("제거된 상자");

    private final String description;
}
