package com.example.keephamchat.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamchat.dto.ChatMessageRequest;
import com.example.keephamchat.entity.ChatMessage;
import com.example.keephamchat.repository.ChatMessageRepository;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional(transactionManager = "mongoTransactionManager")
@Rollback
public class ChatroomServiceTest {

    @Mock
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatroomService chatroomService;

    @Test
    public void sendMessage_Success() {
        // Given
        ChatMessageRequest request = new ChatMessageRequest("chatroomId", "message");
        String userId = "userId";
        ChatMessage chatMessage = ChatMessageRequest.toChatMessage(request, userId);

        RecordMetadata recordMetadata = new RecordMetadata(null, 0, 0, 0, Long.valueOf(0), 0, 0);
        SendResult<String, ChatMessage> sendResult = new SendResult<>(null, recordMetadata);
        CompletableFuture<SendResult<String, ChatMessage>> future = CompletableFuture.completedFuture(sendResult);

        when(kafkaTemplate.send(anyString(), anyString(), any(ChatMessage.class))).thenReturn(future);
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        // When
        chatroomService.sendMessage(request, userId);

        // Then
        verify(chatMessageRepository).save(any(ChatMessage.class));
        verify(kafkaTemplate).send(anyString(), anyString(), any(ChatMessage.class));
    }

    @Test
    public void sendMessage_KafkaSendFail_Rollback() {
        // Given
        ChatMessageRequest request = new ChatMessageRequest("chatroomId", "message");
        String userId = "userId";
        ChatMessage chatMessage = ChatMessageRequest.toChatMessage(request, userId);

        CompletableFuture<SendResult<String, ChatMessage>> future = new CompletableFuture<>();
        future.completeExceptionally(new KafkaException("Kafka send failed"));

        when(kafkaTemplate.send(anyString(), anyString(), any(ChatMessage.class))).thenReturn(future);

        // When & Then
        CompletionException exception = catchThrowableOfType(() -> {
            chatroomService.sendMessage(request, userId);
        }, CompletionException.class);

        assertThat(exception).isNotNull();
        assertThat(exception).hasCauseInstanceOf(KafkaException.class);
        assertThat(exception.getCause()).hasMessageContaining("Kafka send failed");

        verify(chatMessageRepository).save(any(ChatMessage.class));
        verify(kafkaTemplate).send(anyString(), anyString(), any(ChatMessage.class));
        assertThat(chatMessageRepository.findAll()).isEmpty();
    }
}
