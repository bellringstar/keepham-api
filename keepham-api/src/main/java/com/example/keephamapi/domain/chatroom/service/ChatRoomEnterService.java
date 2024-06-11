package com.example.keephamapi.domain.chatroom.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.common.utils.ValidationUtils;
import com.example.keephamapi.domain.box.entity.UnitBox;
import com.example.keephamapi.domain.box.entity.enums.UnitBoxStatus;
import com.example.keephamapi.domain.chatroom.dto.enter.ChatRoomEnterRequest;
import com.example.keephamapi.domain.chatroom.dto.enter.ChatRoomEnterResponse;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.ChatRoomMember;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomMemberStatus;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.repository.ChatRoomMemberRepository;
import com.example.keephamapi.domain.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatRoomEnterService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ValidationUtils validationUtils;

    public ChatRoomEnterResponse enterChatRoom(ChatRoom chatRoom, ChatRoomEnterRequest request, Member member) {
        //1. chatroom이 비밀방이라면 request에 비밀번호와 동일한지 확인. ++ 방 최대 인원인지 확인 필요.
        if (chatRoom.isLocked()) {
            isPasswordCorrect(chatRoom.getPassword(), request.getRoomPassword());
        }
        checkIfChatRoomCanBeEntered(chatRoom);
        //2. 사용자에게 박스 할당.
        List<UnitBox> availableUnitBoxes = chatRoom.getBoxGroup().getBoxes().stream()
                .filter(c -> c.getStatus().equals(UnitBoxStatus.AVAILABLE))
                .collect(Collectors.toList());

        UnitBox assignedBox = assignUnitBoxToMember(member, availableUnitBoxes, request.getBoxPassword());

        //3. db 저장
        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .unitBox(assignedBox)
                .enterDate(LocalDateTime.now())
                .status(ChatRoomMemberStatus.IN)
                .build();

        validationUtils.validate(chatRoomMember);
        chatRoomMemberRepository.save(chatRoomMember);
        //TODO: 4. redis 저장.

        return ChatRoomEnterResponse.builder()
                .box(assignedBox)
                .boxPassword(assignedBox.getPassword())
                .build();
    }

    private UnitBox assignUnitBoxToMember(Member member, List<UnitBox> availableUnitBoxes, String password) {
        if (availableUnitBoxes.isEmpty()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "사용할 수 있는 박스가 없습니다.");
        }
        UnitBox unitBox = availableUnitBoxes.get(0);
        if (password == null) {
            unitBox.assignBoxToMember(member);
            return unitBox;
        }

        unitBox.assignBoxToMember(member, password);
        return unitBox;
    }

    private void isPasswordCorrect(String chatRoomPassword, String inputPassword) {
        if (!chatRoomPassword.equals(inputPassword)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "채팅방 암호가 틀립니다.");
        }
    }

    private void checkIfChatRoomCanBeEntered(ChatRoom chatRoom) {
        if (!isUnderMaxCapacity(chatRoom)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "채팅방이 최대 인원입니다.");
        }

        if (!isChatRoomOpened(chatRoom)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "종료된 채팅방입니다.");
        }
    }

    private boolean isUnderMaxCapacity(ChatRoom chatRoom) {
        // 현재 chatRoom의 인원을 알아야한다. = repository에서 찾아와야한다.
        int count = chatRoomMemberRepository.countChatRoomMemberByChatRoomAndStatus(chatRoom, ChatRoomMemberStatus.IN);
        return count < chatRoom.getMaxPeople();
    }

    private boolean isChatRoomOpened(ChatRoom chatRoom) {
        return chatRoom.getStatus().equals(ChatRoomStatus.OPEN);
    }

}
