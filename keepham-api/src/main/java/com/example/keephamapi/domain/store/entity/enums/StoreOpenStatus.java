package com.example.keephamapi.domain.store.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreOpenStatus {

    OPEN("영업중"),
    CLOSE("영업 종료");

    private final String description;
}
