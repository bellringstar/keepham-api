package com.example.keephamchat.controller;

import com.example.keephamchat.dto.ChatMessageRequest;
import com.example.keephamchat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatroomService chatroomService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageRequest request, WebSocketSession session) {
        chatroomService.sendMessage(request, getUserIdFromSession(session));
    }

    private String getUserIdFromSession(WebSocketSession session) {
        return (String) session.getAttributes().get("userId");
    }
}
