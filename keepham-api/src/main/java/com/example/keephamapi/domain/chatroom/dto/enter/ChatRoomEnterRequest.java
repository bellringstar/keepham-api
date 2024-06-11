package com.example.keephamapi.domain.chatroom.dto.enter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class ChatRoomEnterRequest {

    @NotNull(message = "채팅방 ID는 필수 항목입니다.")
    private Long chatRoomId;

    private String roomPassword;

    @Pattern(regexp = "\\d{4}", message = "박스 비밀번호는 숫자로 이루어진 4자리 문자열이어야 합니다.")
    private String boxPassword;

}
