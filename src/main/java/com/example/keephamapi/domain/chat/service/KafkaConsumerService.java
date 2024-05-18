package com.example.keephamapi.domain.chat.service;

import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-kafka.properties")
public class KafkaConsumerService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topicPattern = "chat-.*", groupId = "${kafka.chat.group-id}")
    public void consumeMessage(ChatMessage message) {
        chatMessageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/" + message.getRoomId(), message); // WebSocket으로 전송
    }
}
