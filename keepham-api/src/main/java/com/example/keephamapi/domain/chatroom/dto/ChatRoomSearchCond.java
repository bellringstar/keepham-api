package com.example.keephamapi.domain.chatroom.dto;

import com.example.keephamapi.common.entity.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomSearchCond {

    private String title;
    private String storeName;

}
