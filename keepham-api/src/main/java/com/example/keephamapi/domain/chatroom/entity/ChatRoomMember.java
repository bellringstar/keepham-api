package com.example.keephamapi.domain.chatroom.entity;

import com.example.keephamapi.domain.box.entity.UnitBox;
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
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "가게 ID는 필수 항목입니다.")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull(message = "회원 ID는 필수 항목입니다.")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_box_id")
    @NotNull(message = "unit box ID는 필수 항목입니다.")
    private UnitBox unitBox;

    @NotNull(message = "입장 시간은 필수 항목입니다.")
    private LocalDateTime enterDate;

    private LocalDateTime exitDate;

    @NotNull(message = "현재 상태는 필수 항목입니다.")
    private ChatRoomMemberStatus status;

    @Builder
    public ChatRoomMember(ChatRoom chatRoom, Member member, UnitBox unitBox, LocalDateTime enterDate, LocalDateTime exitDate,
                          ChatRoomMemberStatus status) {
        this.chatRoom = chatRoom;
        chatRoom.getChatRoomMembers().add(this);
        this.member = member;
        this.unitBox = unitBox;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
        this.status = status;
    }
}
