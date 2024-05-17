package com.example.keephamapi.domain.box.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.entity.enums.BoxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBoxResponse {

    private BoxStatus status;

    private Address address;

    private Coordinate coordinate;

    private String password; //box를 열 수 있는 패스워드

    public static CreateBoxResponse toResponse(Box box) {
        return CreateBoxResponse.builder()
                .address(box.getAddress())
                .coordinate(box.getCoordinate())
                .status(box.getStatus())
                .password(box.getPassword())
                .build();
    }
}
