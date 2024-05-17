package com.example.keephamapi.domain.chatroom.service;

import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.service.BoxViewService;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.service.MemberViewService;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.service.StoreViewService;
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
    private final MemberViewService memberViewService;
    private final BoxViewService boxViewService;
    private final StoreViewService storeViewService;

    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request, String loginId) {

        Member member = memberViewService.findMemberByLoginId(loginId);
        Store store = storeViewService.findStoreById(request.getStoreId());
        Box box = boxViewService.getAvailableBoxById(request.getBoxId());

        box.useBox();

        ChatRoom chatRoom = ChatRoom.builder()
                .title(request.getTitle())
                .status(ChatRoomStatus.OPEN)
                .maxPeople(request.getMaxPeople())
                .superUserId(loginId)
                .locked(request.isLocked())
                .password(request.getPassword())
                .store(store)
                .box(box)
                .build();

        member.enterChatRoom(chatRoom);

        chatRoomRepository.save(chatRoom);
        return ChatRoomCreateResponse.toResponse(chatRoom);
    }
}
