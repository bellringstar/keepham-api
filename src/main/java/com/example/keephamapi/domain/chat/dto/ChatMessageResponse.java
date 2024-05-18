package com.example.keephamapi.domain.chat.dto;

import com.example.keephamapi.domain.chat.entity.ChatMessage;
import com.example.keephamapi.domain.chat.entity.enums.MessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {

    private String id;

    private Long roomId;

    private String senderId;

    private String content;

    private LocalDateTime timestamp;

    private MessageType messageType;

    public static ChatMessageResponse toResponse(ChatMessage message) {
        return ChatMessageResponse.builder()
                .id(message.getId())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .messageType(message.getMessageType())
                .build();
    }
}
