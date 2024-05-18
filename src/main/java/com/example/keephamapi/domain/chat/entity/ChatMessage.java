package com.example.keephamapi.domain.chat.entity;

import com.example.keephamapi.domain.chat.entity.enums.MessageType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;

    private Long roomId;

    private String senderId;

    private String content;

    private LocalDateTime timestamp;

    private MessageType messageType;

    @Builder
    public ChatMessage(Long roomId, String senderId, String content, LocalDateTime timestamp, MessageType messageType) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
        this.messageType = messageType;
    }
}
