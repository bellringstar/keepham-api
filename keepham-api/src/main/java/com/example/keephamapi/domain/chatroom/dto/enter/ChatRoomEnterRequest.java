package com.example.keephamapi.domain.chatroom.dto.enter;

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
public class ChatRoomEnterRequest {

    private Long chatRoomId;
    private String roomPassword; //null 허용
    private String boxPassword; //TODO: null 허용 4자리수로 제한 필요

}
