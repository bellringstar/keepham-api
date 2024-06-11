package com.example.keephamapi.domain.chatroom.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ChatRoomCreateRequest {

    @NotBlank(message = "방 제목은 필수 항목입니다.")
    private String title; //방제목

    @NotNull(message = "최대 인원은 필수 항목입니다.")
    @Min(value = 1, message = "최대 인원은 최소 1명 이상이어야 합니다.")
    @Max(value = 6, message = "최대 인원은 6명을 초과할 수 없습니다.")
    private Integer maxPeople; //방 최대 인원

    @NotNull(message = "공개방 여부는 필수 항목입니다.")
    private boolean locked; //공개방 비공개방

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 설정해야 합니다.")
    private String password; //비공개일시 패스워드

    @NotNull(message = "가게 ID는 필수 항목입니다.")
    private Long storeId; //해당 채팅방에 연결된 가게

    @NotNull(message = "박스 ID는 필수 항목입니다.")
    private Long boxGroupId; //해당 채팅방에 연동된 box

}
