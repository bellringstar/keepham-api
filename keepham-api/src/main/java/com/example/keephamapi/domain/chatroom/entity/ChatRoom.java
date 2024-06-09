package com.example.keephamapi.domain.chatroom.entity;

import com.example.keephamapi.common.entity.BaseTimeEntity;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    private String title; //방제목

    @Enumerated(EnumType.STRING)
    private ChatRoomStatus status;

    private Integer maxPeople; //방 최대 인원

    private String superUserId; //해당 방 방장

    private boolean locked; //공개방 비공개방

    private String password; //비공개일시 패스워드

    private LocalDateTime closedDate; //해당 방이 종료상태가 된 시간

    @OneToOne(fetch = FetchType.LAZY)
    private Store store; //해당 채팅방에 연결된 가게

    @OneToOne(fetch = FetchType.LAZY)
    private Box box; //해당 채팅방에 연동된 box

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @Builder
    public ChatRoom(String title, ChatRoomStatus status, Integer maxPeople, String superUserId, boolean locked,
                    String password, LocalDateTime closedDate, Store store, Box box) {
        this.title = title;
        this.status = status;
        this.maxPeople = maxPeople;
        this.superUserId = superUserId;
        this.locked = locked;
        this.password = password;
        this.closedDate = closedDate;
        this.store = store;
        this.box = box;
    }
}
