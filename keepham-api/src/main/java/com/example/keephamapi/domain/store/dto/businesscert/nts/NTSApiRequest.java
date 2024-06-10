package com.example.keephamapi.domain.store.dto.businesscert.nts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NTSApiRequest {

    @JsonProperty("businesses")
    private List<Business> businesses;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Business {

        @JsonProperty("b_no")
        private String businessNumber;

        @JsonProperty("start_dt")
        private String startDate;

        @JsonProperty("p_nm")
        private String representativeName;

        @JsonProperty("p_nm2")
        private String representativeName2;

        @JsonProperty("b_nm")
        private String businessName;

        @JsonProperty("corp_no")
        private String corporateNumber;

        @JsonProperty("b_sector")
        private String businessSector;

        @JsonProperty("b_type")
        private String businessType;

        @JsonProperty("b_adr")
        private String businessAddress;
    }
}
