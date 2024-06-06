package com.example.keephamchat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "messages")
public class ChatMessage implements Serializable {

    @Id
    private String id;
    private String chatroomId;
    private String userId;
    private String message;
    private LocalDateTime createdAt;

    @Builder
    public ChatMessage(String chatroomId, String userId, String message) {
        this.chatroomId = chatroomId;
        this.userId = userId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }
}
