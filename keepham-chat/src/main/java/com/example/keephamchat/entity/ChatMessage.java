package com.example.keephamchat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "messages")
@NoArgsConstructor
@EqualsAndHashCode
public class ChatMessage implements Serializable {

    @Id
    private String id;

    @Indexed
    private String chatroomId;
    private String userId;
    private String message;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime createdAt;

    @Builder
    public ChatMessage(String chatroomId, String userId, String message) {
        this.chatroomId = chatroomId;
        this.userId = userId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }
}
