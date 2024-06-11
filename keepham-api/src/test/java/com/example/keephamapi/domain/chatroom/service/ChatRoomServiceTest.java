package com.example.keephamapi.domain.chatroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamapi.common.utils.ValidationUtils;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.dto.enter.ChatRoomEnterRequest;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.store.entity.Store;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
@Rollback
public class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatRoomEnterService chatRoomEnterService;

    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private ChatRoomService chatRoomService;

    private ChatRoomCreateRequest validRequest;
    private ChatRoomCreateRequest invalidRequest;
    private Member member;
    private Store store;
    private BoxGroup boxGroup;
    private ChatRoom chatRoom;

    @BeforeEach
    void setUp() {
        validRequest = ChatRoomCreateRequest.builder()
                .title("Sample Room")
                .maxPeople(6)
                .locked(false)
                .password(null)
                .storeId(1L)
                .boxGroupId(1L)
                .build();

        invalidRequest = ChatRoomCreateRequest.builder()
                .title("")
                .maxPeople(0)
                .locked(true)
                .password("")
                .storeId(null)
                .boxGroupId(null)
                .build();

        member = Member.builder()
                .loginId("user1")
                .build();

        store = Store.builder()
                .build();

        boxGroup = BoxGroup.builder()
                .build();

        chatRoom = ChatRoom.builder()
                .title("Sample Room")
                .status(ChatRoomStatus.OPEN)
                .maxPeople(6)
                .superUserId("user1")
                .locked(false)
                .password(null)
                .store(store)
                .boxGroup(boxGroup)
                .build();
    }

    @Test
    void createChatRoom_ShouldReturnSavedChatRoom() {
        //Given
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);

        //When
        ChatRoomCreateResponse response = chatRoomService.createChatRoom(validRequest, member, store, boxGroup);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Sample Room");
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
        verify(chatRoomEnterService, times(1)).enterChatRoom(any(ChatRoom.class), any(ChatRoomEnterRequest.class),
                eq(member));
    }

    @Test
    void createChatRoom_withInvalidEntity_ShouldThrowException() {

        doThrow(new ConstraintViolationException("유효하지 않은 데이터입니다.", null)).when(validationUtils)
                .validate(any(ChatRoom.class));

        // When / Then
        assertThatThrownBy(() -> chatRoomService.createChatRoom(invalidRequest, member, store, boxGroup))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("유효하지 않은 데이터입니다.");
    }

}