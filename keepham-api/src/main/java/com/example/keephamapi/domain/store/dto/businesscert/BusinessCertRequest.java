package com.example.keephamapi.domain.store.dto.businesscert;

import java.time.LocalDate;
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
public class BusinessCertRequest {

    private String businessNumber; //숫자로 이루어진 10자리값
    private String representativeName; //외국인 사업자의 경우 영문명
    private String openingDate; //YYYYMMDD 포맷으로 국세청api로 요청해야 하는 값

}
