package com.example.keephamchat.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamchat.dto.ChatMessageRequest;
import com.example.keephamchat.entity.ChatMessage;
import com.example.keephamchat.repository.ChatMessageRepository;
import java.util.concurrent.CompletableFuture;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@ExtendWith(MockitoExtension.class)
public class ChatroomServiceTest {

    @Mock
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private KafkaTransactionManager<String, ChatMessage> kafkaTransactionManager;

    @InjectMocks
    private ChatroomService chatroomService;

    @Captor
    private ArgumentCaptor<ChatMessage> chatMessageCaptor;

    @Mock
    private TransactionStatus transactionStatus;

    @BeforeEach
    public void setUp() {
        when(kafkaTransactionManager.getTransaction(any(DefaultTransactionDefinition.class)))
                .thenReturn(transactionStatus);
    }

    @Test
    public void sendMessage_successful() {
        ChatMessageRequest request = new ChatMessageRequest("test-chatroom", "test message");
        String userId = "test-user";

        RecordMetadata recordMetadata = new RecordMetadata(
                new org.apache.kafka.common.TopicPartition("chatroom-topic-0", 0),
                0, 0, System.currentTimeMillis(), Long.valueOf(0), 0, 0);

        SendResult<String, ChatMessage> sendResult = new SendResult<>(
                new org.apache.kafka.clients.producer.ProducerRecord<>("chatroom-topic-0", "test-key",
                        new ChatMessage()), recordMetadata);

        when(kafkaTemplate.send(anyString(), anyString(), any(ChatMessage.class)))
                .thenReturn(CompletableFuture.completedFuture(sendResult));

        chatroomService.sendMessage(request, userId);

        verify(chatMessageRepository).save(chatMessageCaptor.capture());
        ChatMessage savedMessage = chatMessageCaptor.getValue();
        assert savedMessage.getUserId().equals(userId);
        assert savedMessage.getChatroomId().equals(request.getChatroomId());
        assert savedMessage.getMessage().equals(request.getMessage());

        verify(kafkaTransactionManager).commit(transactionStatus);
        verify(kafkaTemplate).send(anyString(), anyString(), any(ChatMessage.class));
    }

    @Test
    public void sendMessage_failure() {
        ChatMessageRequest request = new ChatMessageRequest("test-chatroom", "test message");
        String userId = "test-user";

        when(kafkaTemplate.send(anyString(), anyString(), any(ChatMessage.class)))
                .thenReturn(CompletableFuture.failedFuture(new KafkaException("Kafka send failed")));

        assertThrows(org.springframework.kafka.KafkaException.class,
                () -> chatroomService.sendMessage(request, userId));

        verify(chatMessageRepository, never()).save(any(ChatMessage.class));
        verify(kafkaTransactionManager).rollback(transactionStatus);
        verify(kafkaTemplate).send(anyString(), anyString(), any(ChatMessage.class));
    }
}
