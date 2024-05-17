package com.example.keephamapi.domain.box.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.Box;
import com.example.keephamapi.domain.box.entity.enums.BoxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BoxResponse {

    private Long id;

    private BoxStatus status;

    private Address address;

    private Coordinate coordinate;

    private String password; //box를 열 수 있는 패스워드

    public static BoxResponse toResponse(Box box) {
        return BoxResponse.builder()
                .id(box.getId())
                .address(box.getAddress())
                .coordinate(box.getCoordinate())
                .status(box.getStatus())
                .password(box.getPassword())
                .build();
    }
}
