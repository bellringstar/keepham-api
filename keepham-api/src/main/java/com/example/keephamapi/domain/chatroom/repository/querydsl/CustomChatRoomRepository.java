package com.example.keephamapi.domain.chatroom.repository.querydsl;

import com.example.keephamapi.domain.chatroom.dto.ChatRoomSearchCond;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomChatRoomRepository {

    Page<ChatRoom> searchChatRoom(ChatRoomSearchCond chatRoomSearchCond, Pageable pageable);
}
