package com.example.keephamapi.domain.chatroom.repository.querydsl;

import static com.example.keephamapi.domain.box.entity.QBox.box;
import static com.example.keephamapi.domain.chatroom.entity.QChatRoom.chatRoom;
import static com.example.keephamapi.domain.store.entity.QStore.store;

import com.example.keephamapi.domain.chatroom.dto.ChatRoomResponse;
import com.example.keephamapi.domain.chatroom.dto.ChatRoomSearchCond;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@Slf4j
public class CustomChatRoomRepositoryImpl implements
        CustomChatRoomRepository {

    private final JPAQueryFactory queryFactory;

    public CustomChatRoomRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ChatRoom> searchChatRoom(ChatRoomSearchCond chatRoomSearchCond, Pageable pageable) {

        List<ChatRoom> content = queryFactory
                .selectFrom(chatRoom)
                .leftJoin(chatRoom.store, store)
                .fetchJoin()
                .leftJoin(chatRoom.box, box)
                .fetchJoin()
                .where(chatRoomTitleEq(chatRoomSearchCond.getTitle()),
                        storeNameEq(chatRoomSearchCond.getStoreName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        long total = queryFactory
                .select(chatRoom.count())
                .from(chatRoom)
                .leftJoin(chatRoom.store, store)
                .leftJoin(chatRoom.box, box)
                .where(chatRoomTitleEq(chatRoomSearchCond.getTitle()),
                        storeNameEq(chatRoomSearchCond.getStoreName()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression chatRoomTitleEq(String title) {
        return StringUtils.hasText(title) ? chatRoom.title.eq(title) : null;
    }

    private BooleanExpression storeNameEq(String name) {
        return StringUtils.hasText(name) ? chatRoom.store.name.eq(name) : null;
    }
}
