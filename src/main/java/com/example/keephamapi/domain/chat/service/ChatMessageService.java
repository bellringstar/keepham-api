package com.example.keephamapi.domain.chat.service;

import com.example.keephamapi.domain.chat.dto.ChatMessageRequest;
import com.example.keephamapi.domain.chat.dto.ChatMessageResponse;
import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.repository.ChatMessageRepository;
import java.time.LocalDateTime;
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

    private final String SYSTEM = "system";
    private final String TOPIC = "chat-";

    private void save(ChatMessageRequest request, String loginId) {

        ChatMessage chatMessage = ChatMessage
                .builder()
                .roomId(request.getRoomId())
                .senderId(loginId)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessage);
    }

    public void send(ChatMessageRequest request, String loginId) {
        save(request, loginId);
        kafkaProducerService.sendMessage(TOPIC + request.getRoomId(), request);
    }

    public void broadcast(ChatMessageRequest request) {
        save(request, SYSTEM);
        kafkaProducerService.sendMessage(TOPIC + request.getRoomId(), request);
    }
}
