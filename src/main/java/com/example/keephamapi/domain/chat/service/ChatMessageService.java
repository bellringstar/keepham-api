package com.example.keephamapi.domain.chat.service;

import com.example.keephamapi.domain.chat.dto.ChatMessageRequest;
import com.example.keephamapi.domain.chat.dto.ChatMessageResponse;
import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final KafkaProducerService kafkaProducerService;

    public ChatMessageResponse save(ChatMessageRequest request, Authentication auth) {

        ChatMessage chatMessage = ChatMessage
                .builder()
                .roomId(request.getRoomId())
                .senderId(auth.getName())
                .content(request.getContent())
                .timestamp(request.getTimestamp())
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.toResponse(chatMessage);
    }

    public void send(ChatMessageRequest request, Authentication auth) {
        save(request, auth);
        kafkaProducerService.sendMessage("chatting", request);
    }
}
