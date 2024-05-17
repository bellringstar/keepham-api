package com.example.keephamapi.domain.chatroom.dto;

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
public class ChatRoomCreateRequest {

    private String title; //방제목

    private Integer maxPeople; //방 최대 인원

    private String superUserId; //해당 방 방장

    private boolean locked; //공개방 비공개방

    private String password; //비공개일시 패스워드

    private Long storeId; //해당 채팅방에 연결된 가게

    private Long boxId; //해당 채팅방에 연동된 box

}
