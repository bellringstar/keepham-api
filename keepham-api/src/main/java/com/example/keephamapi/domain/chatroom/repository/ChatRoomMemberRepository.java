package com.example.keephamapi.domain.chatroom.repository;

import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.ChatRoomMember;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomMemberStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    int countChatRoomMemberByChatRoomAndStatus(ChatRoom chatRoom, ChatRoomMemberStatus status);
}

