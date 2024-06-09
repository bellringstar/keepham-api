package com.example.keephamapi.domain.box.controller;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.domain.box.dto.BoxGroupCreateRequest;
import com.example.keephamapi.domain.box.dto.BoxGroupCreateResponse;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.service.BoxGroupService;
import com.example.keephamapi.domain.box.service.BoxGroupViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/box-group")
@RequiredArgsConstructor
@Slf4j
public class BoxGroupController {

    private final BoxGroupService boxGroupService;
    private final BoxGroupViewService boxGroupViewService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Api<BoxGroupCreateResponse> creatBoxGroup(@RequestBody BoxGroupCreateRequest request) {

        BoxGroup boxGroup = boxGroupService.createBoxGroup(request);

        return Api.OK(BoxGroupCreateResponse.toResponse(boxGroup));
    }
}
