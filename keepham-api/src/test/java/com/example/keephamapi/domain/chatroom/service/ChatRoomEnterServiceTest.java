package com.example.keephamapi.domain.chatroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.common.utils.ValidationUtils;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.UnitBox;
import com.example.keephamapi.domain.box.entity.enums.UnitBoxStatus;
import com.example.keephamapi.domain.chatroom.dto.enter.ChatRoomEnterRequest;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.ChatRoomMember;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomMemberStatus;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomMemberRepository;
import com.example.keephamapi.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
@Rollback
class ChatRoomEnterServiceTest {

    @Mock
    private ChatRoomMemberRepository chatRoomMemberRepository;

    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private ChatRoomEnterService chatRoomEnterService;

    private ChatRoom chatRoom;
    private Member member;
    private ChatRoomEnterRequest request;

    @BeforeEach
    void setUp() {
        member = Member.builder().build();
        request = new ChatRoomEnterRequest();
        chatRoom = ChatRoom.builder()
                .password("correct_password")
                .locked(true)
                .status(ChatRoomStatus.OPEN)
                .maxPeople(5)
                .boxGroup(BoxGroup.builder().build())
                .build();

        UnitBox box1 = UnitBox.builder().status(UnitBoxStatus.AVAILABLE).build();
        UnitBox box2 = UnitBox.builder().status(UnitBoxStatus.AVAILABLE).build();
        chatRoom.getBoxGroup().getBoxes().add(box1);
        chatRoom.getBoxGroup().getBoxes().add(box2);
    }

    @Test
    void enterChatRoom_Success() {
        //given
        request.setRoomPassword("correct_password");
        when(chatRoomMemberRepository.countChatRoomMemberByChatRoomAndStatus(any(), any()))
                .thenReturn(3);

        //when
        chatRoomEnterService.enterChatRoom(chatRoom, request, member);

        //then
        ArgumentCaptor<ChatRoomMember> chatRoomMemberCaptor = ArgumentCaptor.forClass(ChatRoomMember.class);
        verify(chatRoomMemberRepository).save(chatRoomMemberCaptor.capture());
        ChatRoomMember savedChatRoomMember = chatRoomMemberCaptor.getValue();

        assertThat(savedChatRoomMember.getChatRoom()).isEqualTo(chatRoom);
        assertThat(savedChatRoomMember.getMember()).isEqualTo(member);
        assertThat(savedChatRoomMember.getEnterDate()).isNotNull();
        assertThat(savedChatRoomMember.getStatus()).isEqualTo(ChatRoomMemberStatus.IN);
    }

    @Test
    void enterChatRoom_IncorrectPassword() {
        // given
        request.setRoomPassword("wrong_password");

        // when & then
        assertThatThrownBy(() -> chatRoomEnterService.enterChatRoom(chatRoom, request, member))
                .isInstanceOf(ApiException.class)
                .hasMessage("채팅방 암호가 틀립니다.");
    }

    @Test
    void enterChatRoom_MaxCapacityExceeded() {
        //given
        request.setRoomPassword("correct_password");
        when(chatRoomMemberRepository.countChatRoomMemberByChatRoomAndStatus(any(), any()))
                .thenReturn(5);

        // when & then
        assertThatThrownBy(() -> chatRoomEnterService.enterChatRoom(chatRoom, request, member))
                .isInstanceOf(ApiException.class)
                .hasMessage("채팅방이 최대 인원입니다.");
    }

    @Test
    void enterChatRoom_ChatRoomClosed() {
        //given
        request.setRoomPassword("correct_password");
        chatRoom.closeChatRoom();
        //when && then
        assertThatThrownBy(() -> chatRoomEnterService.enterChatRoom(chatRoom, request, member))
                .isInstanceOf(ApiException.class)
                .hasMessage("종료된 채팅방입니다.");
    }

    @Test
    void enterChatRoom_NoAvailableBoxes() {
        // given
        request.setRoomPassword("correct_password");
        when(chatRoomMemberRepository.countChatRoomMemberByChatRoomAndStatus(any(), any()))
                .thenReturn(3);
        chatRoom.getBoxGroup().getBoxes().clear();

        // when & then
        assertThatThrownBy(() -> chatRoomEnterService.enterChatRoom(chatRoom, request, member))
                .isInstanceOf(ApiException.class)
                .hasMessage("사용할 수 있는 박스가 없습니다.");
    }
}