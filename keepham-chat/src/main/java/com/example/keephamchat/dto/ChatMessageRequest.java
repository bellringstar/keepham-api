package com.example.keephamchat.dto;

import com.example.keephamchat.entity.ChatMessage;
import java.io.Serializable;
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
public class ChatMessageRequest implements Serializable {

    private String chatroomId;
    private String message;

    @Override
    public String toString() {
        return "ChatMessageRequest{" +
                "chatroomId='" + chatroomId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static ChatMessage toChatMessage(ChatMessageRequest request, String userId) {
        return ChatMessage.builder()
                .userId(userId)
                .message(request.message)
                .chatroomId(request.chatroomId)
                .build();
    }
}
