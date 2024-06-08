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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
        if (userId == null) {
            //TODO: 세션에서 유저ID 가져오는 곳으로 이동 후 예외를 던지는 방식으로 변경
            log.error("세션에 유저 정보가 없습니다.");
            return;
        }

        ChatMessage chatMessage = ChatMessageRequest.toChatMessage(request, userId);
        chatMessageRepository.save(chatMessage);

        String topic = getTopicFromChatroom(request.getChatroomId());
        log.info("sendMessage : {}, topic : {}", request, topic);
        sendKafkaMessageWithTransaction(topic, request.getChatroomId(), chatMessage);
    }

    private void sendKafkaMessageWithTransaction(String topic, String key, ChatMessage message) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = kafkaTransactionManager.getTransaction(def);

        try {
            CompletableFuture<SendResult<String, ChatMessage>> future = kafkaTemplate.send(topic, key, message);

            future.thenAccept(result -> {
                        log.info("Message : {} delivered with offset {}", message, result.getRecordMetadata().offset());
                        kafkaTransactionManager.commit(status);
                    })
                    .exceptionally(ex -> {
                        log.error("Unable to deliver message [{}]. {}", message, ex.getMessage());
                        throw new KafkaException("kafka send failed", ex);
                    }).join();
        } catch (Exception e) {
            kafkaTransactionManager.rollback(status);
            throw new KafkaException("kafka send failed", e);
        }
    }

    private String getTopicFromChatroom(String chatroomId) {
        //TODO: 안정 해시 적용
        int hash = Math.abs(chatroomId.hashCode());
        int topicIndex = hash % NUM_TOPICS;
        return "chatroom-topic-" + topicIndex;
    }

}
