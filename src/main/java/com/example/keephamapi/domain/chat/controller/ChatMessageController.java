package com.example.keephamapi.domain.chat.controller;

import com.example.keephamapi.domain.chat.dto.ChatMessageRequest;
import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.service.ChatMessageService;
import com.example.keephamapi.domain.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessageRequest request, Authentication auth) {
        chatMessageService.send(request, auth);
    }
}
