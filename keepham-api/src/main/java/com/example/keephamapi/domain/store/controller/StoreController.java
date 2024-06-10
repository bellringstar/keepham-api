package com.example.keephamapi.domain.store.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.store.dto.store.StoreCreateRequest;
import com.example.keephamapi.domain.store.dto.store.StoreCreateResponse;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.service.BusinessCertService;
import com.example.keephamapi.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final BusinessCertService businessCertService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public Api<StoreCreateResponse> enrollStore(@RequestBody @Valid StoreCreateRequest request, Authentication auth) {

        boolean valid = businessCertService.verifyBusinessCertInfo(request.getBusinessCertRequest());
        if (!valid) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "유효하지 않은 사업자정보입니다.");
        }
        Store store = storeService.createStore(request);
        businessCertService.saveBusinessCertInfo(request.getBusinessCertRequest(), store);
        return Api.OK(StoreCreateResponse.toResponse(store));
    }
}
