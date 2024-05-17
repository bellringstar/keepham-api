package com.example.keephamapi.domain.chatroom.dto;

import com.example.keephamapi.domain.box.dto.BoxResponse;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.store.dto.StoreResponse;
import com.example.keephamapi.domain.store.entity.Store;
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
public class ChatRoomCreateResponse {

    private String title; //방제목

    private Integer maxPeople; //방 최대 인원

    private String superUserId; //해당 방 방장

    private boolean locked; //공개방 비공개방

    private String password; //비공개일시 패스워드

    private StoreResponse store; //해당 채팅방에 연결된 가게

    private BoxResponse box; //해당 채팅방에 연동된 box

    public static ChatRoomCreateResponse toResponse(ChatRoom chatRoom) {
        return ChatRoomCreateResponse
                .builder()
                .title(chatRoom.getTitle())
                .maxPeople(chatRoom.getMaxPeople())
                .superUserId(chatRoom.getSuperUserId())
                .locked(chatRoom.isLocked())
                .password(chatRoom.getPassword())
                .store(StoreResponse.toResponse(chatRoom.getStore()))
                .box(BoxResponse.toResponse(chatRoom.getBox()))
                .build();
    }
}
