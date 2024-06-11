package com.example.keephamapi.domain.chatroom.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.service.BoxGroupViewService;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomResponse;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomSearchCond;
import com.example.keephamapi.domain.chatroom.service.ChatRoomService;
import com.example.keephamapi.domain.chatroom.service.ChatRoomViewService;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.service.MemberViewService;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.service.StoreViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatroom")
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomViewService chatRoomViewService;
    private final MemberViewService memberViewService;
    private final BoxGroupViewService boxGroupViewService;
    private final StoreViewService storeViewService;

    @PostMapping
    public Api<ChatRoomCreateResponse> createChatRoom(@RequestBody @Validated ChatRoomCreateRequest request, Authentication auth) {

        Member member = memberViewService.findMemberByLoginId(auth.getName());
        Store store = storeViewService.findStoreById(request.getStoreId());
        BoxGroup boxGroup = boxGroupViewService.findAvailableBoxById(request.getBoxGroupId());

        ChatRoomCreateResponse response = chatRoomService.createChatRoom(request, member, store, boxGroup);

        return Api.OK(response);
    }

    @GetMapping
    public Api<Page<ChatRoomResponse>> getChatRooms(@RequestParam(required = false) String title,
            @RequestParam(required = false) String storeName
            , @PageableDefault Pageable pageable) {

        ChatRoomSearchCond chatRoomSearchCond = ChatRoomSearchCond.builder()
                .title(title)
                .storeName(storeName)
                .build();

        Page<ChatRoomResponse> chatRooms = chatRoomViewService.getChatRooms(chatRoomSearchCond, pageable);

        return Api.OK(chatRooms);
    }
}
