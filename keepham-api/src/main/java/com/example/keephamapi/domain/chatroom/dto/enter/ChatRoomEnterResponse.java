package com.example.keephamapi.domain.chatroom.dto.enter;

import com.example.keephamapi.domain.box.entity.UnitBox;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomResponse;
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
public class ChatRoomEnterResponse {

    private UnitBox box; //사용자에게 할당된 박스 TODO:dto로 변경 필요
    private String password; //null 허용

}
