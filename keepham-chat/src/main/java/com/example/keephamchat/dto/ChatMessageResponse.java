package com.example.keephamchat.dto;

import com.example.keephamchat.entity.ChatMessage;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse implements Serializable {

    private String chatroomId;
    private String userId;
    private String message;
    private LocalDateTime createdAt;

    public static ChatMessageResponse toResponse(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .chatroomId(chatMessage.getChatroomId())
                .userId(chatMessage.getUserId())
                .message(chatMessage.getMessage())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
