package com.example.keephamapi.domain.chat.controller;

import com.example.keephamapi.domain.chat.dto.ChatMessageRequest;
import com.example.keephamapi.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageRequest request, Authentication auth) {
        chatMessageService.send(request, auth.getName());
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessageRequest request) {
        chatMessageService.broadcast(request);
    }
}
