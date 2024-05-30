package com.example.keephamapi.domain.store.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StoreViewService {

    private final StoreRepository storeRepository;

    public Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않은 가게")); //TODO:에러코드 추가로 변경
    }

}
