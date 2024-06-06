package com.example.keephamchat.service;

import com.example.keephamchat.dto.ChatMessageRequest;
import com.example.keephamchat.entity.ChatMessage;
import com.example.keephamchat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void sendMessage(ChatMessageRequest request, String userId) {
        if (userId == null) {
            log.error("세션에 유저 정보가 없습니다.");
            return;
        }
        ChatMessage chatMessage = ChatMessageRequest.toChatMessage(request, userId);
        chatMessageRepository.save(chatMessage);

        String topic = getTopicFromChatroom(request.getChatroomId());
        kafkaTemplate.send(topic, request.getChatroomId(), chatMessage);
    }


    private String getTopicFromChatroom(String chatroomId) {
        //TODO: 안정 해시 적용
        int hash = Math.abs(chatroomId.hashCode());
        int topicIndex = hash % NUM_TOPICS;
        return "chatroom-topic-" + topicIndex;
    }

}
