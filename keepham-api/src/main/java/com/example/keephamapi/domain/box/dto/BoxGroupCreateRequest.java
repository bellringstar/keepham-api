package com.example.keephamapi.domain.box.dto;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BoxGroupCreateRequest {

    @NotNull(message = "박스 상태는 필수 값입니다.")
    private BoxGroupStatus status;

    @NotNull(message = "주소는 필수 값입니다.")
    //TODO: 주소 패턴 지정
    private Address address;

    @NotNull(message = "위치 좌표는 필수 값입니다.")
    private Coordinate coordinate;
}
