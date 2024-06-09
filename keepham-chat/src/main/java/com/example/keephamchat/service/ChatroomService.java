package com.example.keephamchat.service;

import com.example.keephamchat.dto.ChatMessageRequest;
import com.example.keephamchat.entity.ChatMessage;
import com.example.keephamchat.repository.ChatMessageRepository;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * 카프카로 메시지 전송 및 토픽 관리를 위한 service
 * TODO: 채팅방ID 생성 방식 결정
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatroomService {

    private static final int NUM_TOPICS = 10;
    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final KafkaTransactionManager<String, ChatMessage> kafkaTransactionManager;

    @Transactional(transactionManager = "mongoTransactionManager")
    public void sendMessage(ChatMessageRequest request, String userId) {

        ChatMessage chatMessage = ChatMessageRequest.toChatMessage(request, userId);
        chatMessageRepository.save(chatMessage);

        String topic = getTopicFromChatroom(request.getChatroomId());
        log.info("sendMessage : {}, topic : {}", request, topic);
        sendKafkaMessage(request, chatMessage);
    }

    private void sendKafkaMessage(ChatMessageRequest request, ChatMessage chatMessage) {

        String topic = getTopicFromChatroom(request.getChatroomId());
        CompletableFuture<SendResult<String, ChatMessage>> future = kafkaTemplate.send(topic, request.getChatroomId(),
                chatMessage);

        future.thenAccept(result -> log.info("Message : {} delivered with offset {}", chatMessage,
                        result.getRecordMetadata().offset()))
                .exceptionally(ex -> {
                    log.error("Unable to deliver message [{}]. {}", chatMessage, ex.getMessage());
                    throw new KafkaException("Kafka send failed", ex);
                }).join();
    }

    private String getTopicFromChatroom(String chatroomId) {
        //TODO: 안정 해시 적용
        int hash = Math.abs(chatroomId.hashCode());
        int topicIndex = hash % NUM_TOPICS;
        return "chatroom-topic-" + topicIndex;
    }

}
