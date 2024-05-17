package com.example.keephamapi.domain.chatroom.repository;

import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
