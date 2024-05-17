package com.example.keephamapi.domain.chatroom.service;

import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* 화면을 출력을 위한 서비스의 집합
* */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomViewService {

    private final ChatRoomRepository chatRoomRepository;
}
