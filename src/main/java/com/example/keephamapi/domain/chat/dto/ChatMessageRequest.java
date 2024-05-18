package com.example.keephamapi.domain.chat.dto;

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
public class ChatMessageRequest {

    private Long roomId;

    private String content;

    private LocalDateTime timestamp;

    private MessageType messageType;

}
