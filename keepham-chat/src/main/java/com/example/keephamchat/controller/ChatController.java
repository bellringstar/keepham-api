package com.example.keephamchat.controller;

import com.example.keephamchat.dto.ChatMessageRequest;
import com.example.keephamchat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatroomService chatroomService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String userId = getUserIdFromSession(headerAccessor);
        chatroomService.sendMessage(request, userId);
    }

    private String getUserIdFromSession(SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        if (userId == null) {
            log.error("세션에 유저 정보가 없습니다.");
            throw new IllegalStateException("유효한 사용자 세션이 아닙니다.");
        }
        return userId;
    }
}
