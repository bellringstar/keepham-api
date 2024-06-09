package com.example.keephamapi.domain.chatroom.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomResponse;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
import com.example.keephamapi.domain.store.entity.Store;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ChatRoomViewServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomViewService chatRoomViewService;

    @Test
    public void getAllChatRooms_shouldReturnPagedChatRooms() {
        // Given
        Store store = Store.builder()
                .name("Test Store")
                .build();

        BoxGroup boxGroup = BoxGroup.builder()
                .status(BoxGroupStatus.AVAILABLE)
                .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .title("Test Room")
                .status(ChatRoomStatus.OPEN)
                .maxPeople(10)
                .superUserId("superuser")
                .locked(false)
                .password("password")
                .store(store)
                .boxGroup(boxGroup)
                .build();

        Page<ChatRoom> chatRoomPage = new PageImpl<>(Collections.singletonList(chatRoom));
        given(chatRoomRepository.findAllChatRoom(any(Pageable.class))).willReturn(chatRoomPage);

        // When
        Page<ChatRoomResponse> result = chatRoomViewService.getAllChatRooms(PageRequest.of(0, 10));

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Room");
    }
}