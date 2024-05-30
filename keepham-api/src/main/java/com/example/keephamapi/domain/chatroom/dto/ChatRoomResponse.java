package com.example.keephamapi.domain.chatroom.dto;

import com.example.keephamapi.common.entity.BaseTimeEntity;
import com.example.keephamapi.domain.box.dto.BoxResponse;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.member.dto.MemberResponse;
import com.example.keephamapi.domain.store.dto.StoreResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ChatRoomResponse {

    private Long id;

    private String title;

    private Integer maxPeople;

    private String superUserId;

    private boolean locked;

    private String password;

    private StoreResponse store;

    private BoxResponse box;

    private List<MemberResponse> members;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    public static ChatRoomResponse toResponse(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .maxPeople(chatRoom.getMaxPeople())
                .superUserId(chatRoom.getSuperUserId())
                .locked(chatRoom.isLocked())
                .password(chatRoom.getPassword())
                .store(StoreResponse.toResponse(chatRoom.getStore()))
                .box(BoxResponse.toResponse(chatRoom.getBox()))
                .members(chatRoom.getMembers().stream().map(MemberResponse::toResponse).collect(Collectors.toList()))
                .createDate(chatRoom.getCreateDate())
                .modifiedDate(chatRoom.getModifiedDate())
                .build();
    }
}
