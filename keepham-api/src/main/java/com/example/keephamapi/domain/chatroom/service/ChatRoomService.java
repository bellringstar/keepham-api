package com.example.keephamapi.domain.chatroom.service;

import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.dto.enter.ChatRoomEnterRequest;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomEnterService chatRoomEnterService;

    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request, Member member, Store store, BoxGroup boxGroup) {

        boxGroup.useBox();

        ChatRoom chatRoom = ChatRoom.builder()
                .title(request.getTitle())
                .status(ChatRoomStatus.OPEN)
                .maxPeople(request.getMaxPeople())
                .superUserId(member.getLoginId())
                .locked(request.isLocked())
                .password(request.getPassword())
                .store(store)
                .boxGroup(boxGroup)
                .build();

        chatRoomRepository.save(chatRoom);

        ChatRoomEnterRequest chatRoomEnterRequest = ChatRoomEnterRequest.builder()
                .chatRoomId(chatRoom.getId())
                .password(request.getPassword())
                .build();
        // 채팅방 생성한 사람은 자동으로 입장.
        chatRoomEnterService.enterChatRoom(chatRoom, chatRoomEnterRequest, member);
        return ChatRoomCreateResponse.toResponse(chatRoom);
    }
}
