package com.example.keephamapi.domain.store.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    KOREA("한식"),
    JAPAN("일식"),
    CHINA("중식"),;

    private final String description;
}
