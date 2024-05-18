package com.example.keephamapi.domain.chat.service;

import com.example.keephamapi.domain.chat.dto.ChatMessageRequest;
import com.example.keephamapi.domain.chat.dto.ChatMessageResponse;
import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageResponse save(ChatMessageRequest request) {

        ChatMessage chatMessage = ChatMessage
                .builder()
                .roomId(request.getRoomId())
                .senderId(request.getSenderId())
                .content(request.getContent())
                .timestamp(request.getTimestamp())
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.toResponse(chatMessage);
    }
}
