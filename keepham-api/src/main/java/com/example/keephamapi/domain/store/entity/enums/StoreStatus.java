package com.example.keephamapi.domain.store.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {

    PENDING_APPROVAL("승인 대기"),
    APPROVAL("승인"),
    DECLINED("승인 거절"),
    DELETED("삭제");

    private final String description;
}
