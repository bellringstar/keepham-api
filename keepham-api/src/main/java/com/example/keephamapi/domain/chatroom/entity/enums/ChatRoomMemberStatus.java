package com.example.keephamapi.domain.chatroom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatRoomMemberStatus {
    IN("입장"),
    OUT("퇴장"),;

    private final String description;
}
