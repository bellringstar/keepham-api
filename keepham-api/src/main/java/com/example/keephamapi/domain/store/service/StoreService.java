package com.example.keephamapi.domain.store.service;

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
}
