package com.example.keephamapi.domain.store.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.store.dto.StoreCreateRequest;
import com.example.keephamapi.domain.store.dto.StoreCreateResponse;
import com.example.keephamapi.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store")
@Slf4j
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public Api<StoreCreateResponse> createChatRoom(@RequestBody @Valid StoreCreateRequest request, Authentication auth) {
    //todo: 가게 고객만 쓸 수 있도록 인증처리
        log.info("auth : {}", auth.getName());
        StoreCreateResponse response = storeService.createStore(request);

        return Api.OK(response);
    }
}
