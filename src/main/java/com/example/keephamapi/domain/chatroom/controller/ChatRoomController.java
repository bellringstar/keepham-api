package com.example.keephamapi.domain.chatroom.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatroom")
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public Api<ChatRoomCreateResponse> createChatRoom(@RequestBody @Valid ChatRoomCreateRequest request, Authentication auth) {

        ChatRoomCreateResponse response = chatRoomService.createChatRoom(request, auth.getName());

        return Api.OK(response);
    }
}
