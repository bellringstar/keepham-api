package com.example.keephamapi.domain.chatroom.entity;

import com.example.keephamapi.common.entity.BaseTimeEntity;
import com.example.keephamapi.domain.box.entity.BoxGroup;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "방 제목은 필수 항목입니다.")
    private String title; //방제목

    @Enumerated(EnumType.STRING)
    @NotNull(message = "상태는 필수 항목입니다.")
    private ChatRoomStatus status;

    @NotNull(message = "최대 인원은 필수 항목입니다.")
    @Min(value = 1, message = "최대 인원은 최소 1명 이상이어야 합니다.")
    @Max(value = 6, message = "최대 인원은 6명을 초과할 수 없습니다.")
    private Integer maxPeople; //방 최대 인원

    @NotBlank(message = "방장 ID는 필수 항목입니다.")
    private String superUserId; //해당 방 방장

    @NotNull(message = "공개방 여부는 필수 항목입니다.")
    private boolean locked; //공개방 비공개방

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 설정해야 합니다.")
    private String password; //비공개일시 패스워드

    private LocalDateTime closedDate; //해당 방이 종료상태가 된 시간

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(message = "가게 ID는 필수 항목입니다.")
    private Store store; //해당 채팅방에 연결된 가게

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_group_id")
    @NotNull(message = "박스 그룹 ID는 필수 항목입니다.")
    private BoxGroup boxGroup; //해당 채팅방에 연동된 box

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @Builder
    public ChatRoom(String title, ChatRoomStatus status, Integer maxPeople, String superUserId, boolean locked,
                    String password, LocalDateTime closedDate, Store store, BoxGroup boxGroup) {
        this.title = title;
        this.status = status;
        this.maxPeople = maxPeople;
        this.superUserId = superUserId;
        this.locked = locked;
        this.password = password;
        this.closedDate = closedDate;
        this.store = store;
        this.boxGroup = boxGroup;
        boxGroup.getChatRooms().add(this);
    }

    public void closeChatRoom() {
        this.status = ChatRoomStatus.CLOSD;
        //todo: chatroomMembers가 남아있으면 종료 못하게 해야한다.?
    }
}
