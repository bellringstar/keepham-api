package com.example.keephamapi.domain.chatroom.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomResponse;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomSearchCond;
import com.example.keephamapi.domain.chatroom.service.ChatRoomService;
import com.example.keephamapi.domain.chatroom.service.ChatRoomViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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

    @PostMapping
    public Api<ChatRoomCreateResponse> createChatRoom(@RequestBody @Valid ChatRoomCreateRequest request, Authentication auth) {

        ChatRoomCreateResponse response = chatRoomService.createChatRoom(request, auth.getName());

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
