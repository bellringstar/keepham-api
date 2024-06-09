package com.example.keephamapi.domain.box.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
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
public class BoxGroupCreateResponse {

    private BoxGroupStatus status;

    private Address address;

    private Coordinate coordinate;

    private String password; //box를 열 수 있는 패스워드

    public static BoxGroupCreateResponse toResponse(BoxGroup boxGroup) {
        return BoxGroupCreateResponse.builder()
                .address(boxGroup.getAddress())
                .coordinate(boxGroup.getCoordinate())
                .status(boxGroup.getStatus())
                .build();
    }
}
