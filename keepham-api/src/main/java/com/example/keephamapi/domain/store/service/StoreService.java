package com.example.keephamapi.domain.store.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.store.dto.store.StoreCreateRequest;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;

    public Store createStore(StoreCreateRequest request) {

        Store store = StoreCreateRequest.toEntity(request);

        return storeRepository.save(store);
    }

    public Store deleteStore(Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 가게입니다."));

        store.deleteStore();

        // TODO: store에 연결된 채팅방이 존재한다면 해당 채팅방에 알림처리 기능 구현.
        if (!store.getChatRooms().isEmpty()) {
            // 연결된 채팅방이 존재. 참고로 현재 lazy loading 상태.
        }
        return store;
    }
}
