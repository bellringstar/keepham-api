package com.example.keephamapi.domain.chatroom.dto.enter;

import com.example.keephamapi.domain.chatroom.entity.ChatRoomMember;
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
    private String password; //null 허용

}
