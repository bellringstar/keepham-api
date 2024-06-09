package com.example.keephamapi.domain.chatroom.entity;

import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomMemberStatus;
import com.example.keephamapi.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime enterDate;

    private LocalDateTime exitDate;

    private ChatRoomMemberStatus status;

    @Builder
    public ChatRoomMember(ChatRoom chatRoom, Member member, LocalDateTime enterDate, LocalDateTime exitDate,
                          ChatRoomMemberStatus status) {
        this.chatRoom = chatRoom;
        chatRoom.getChatRoomMembers().add(this);
        this.member = member;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
        this.status = status;
    }
}
