package com.example.keephamapi.domain.chatroom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatRoomStatus {
    OPEN("진행중"),
    CLOSD("종료"),;

    private final String description;
}
