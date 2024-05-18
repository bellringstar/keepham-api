package com.example.keephamapi.domain.chat.service;

import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-kafka.properties")
public class KafkaConsumerService {

    private final ChatMessageRepository chatMessageRepository;

    @SendTo("/topic/public")
    @KafkaListener(topics = "${kafka.chat.topic}", groupId = "${kafka.chat.group-id}")
    public ChatMessage consumeMessage(ChatMessage message) {
        chatMessageRepository.save(message);
        return message;
    }
}
