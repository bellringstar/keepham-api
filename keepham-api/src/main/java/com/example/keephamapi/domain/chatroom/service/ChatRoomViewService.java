package com.example.keephamapi.domain.chatroom.service;

import com.example.keephamapi.domain.chatroom.dto.ChatRoomResponse;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomSearchCond;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* 화면을 출력을 위한 서비스의 집합
* */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomViewService {

    private final ChatRoomRepository chatRoomRepository;

    public Page<ChatRoomResponse> getAllChatRooms(Pageable pageable) {

        Page<ChatRoom> allChatRoom = chatRoomRepository.findAllChatRoom(pageable);

        return allChatRoom.map(ChatRoomResponse::toResponse);
    }

    public Page<ChatRoomResponse> getChatRooms(ChatRoomSearchCond cond, Pageable pageable) {

        if (cond == null) {
            return getAllChatRooms(pageable);
        }

        Page<ChatRoom> chatRoomResponses = chatRoomRepository.searchChatRoom(cond, pageable);

        return chatRoomResponses.map(ChatRoomResponse::toResponse);
    }

}
