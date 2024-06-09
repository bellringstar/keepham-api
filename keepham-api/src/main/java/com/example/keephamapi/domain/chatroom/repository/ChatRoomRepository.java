package com.example.keephamapi.domain.chatroom.repository;

import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.querydsl.CustomChatRoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, CustomChatRoomRepository {

    @Query(value = "select c from ChatRoom c join fetch c.store s join fetch c.box b join fetch c.chatRoomMembers crm", countQuery = "select count(c) from ChatRoom c")
    Page<ChatRoom> findAllChatRoom(Pageable pageable);
}
