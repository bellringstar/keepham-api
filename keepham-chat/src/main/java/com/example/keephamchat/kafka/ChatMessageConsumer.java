package com.example.keephamchat.kafka;

import com.example.keephamchat.dto.ChatMessageResponse;
import com.example.keephamchat.entity.ChatMessage;
import com.example.keephamchat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final String DESTINATION_PREFIX = "/topic/chatroom/";

    @KafkaListener(topicPattern = "chatroom-topic-.*", groupId = "chatroom-consumers")
    public void listen(ChatMessage chatMessage) {
        log.info("Received message: {} from chatroom: {}, user: {}", chatMessage.getMessage(),
                chatMessage.getChatroomId(), chatMessage.getUserId());

        //TODO: 해당 채팅방에 접속해있는 인원 중 session 연결이 종료된 인원에게는 알림처리 구현
        String destination = DESTINATION_PREFIX + chatMessage.getChatroomId();
        messagingTemplate.convertAndSend(destination, ChatMessageResponse.toResponse(chatMessage));
    }

}
