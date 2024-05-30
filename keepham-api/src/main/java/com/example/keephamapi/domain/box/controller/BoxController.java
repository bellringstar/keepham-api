package com.example.keephamapi.domain.box.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.box.dto.CreateBoxRequest;
import com.example.keephamapi.domain.box.dto.CreateBoxResponse;
import com.example.keephamapi.domain.box.service.BoxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/box")
@RequiredArgsConstructor
@Slf4j
public class BoxController {

    private final BoxService boxService;

    @PostMapping
    public Api<CreateBoxResponse> createBox(@RequestBody @Valid CreateBoxRequest request, Authentication auth) {
        //todo: 권한 로직 추가

        CreateBoxResponse response = boxService.createBox(request);

        return Api.OK(response);
    }

}
