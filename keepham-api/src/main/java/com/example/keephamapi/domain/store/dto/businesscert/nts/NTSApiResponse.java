package com.example.keephamapi.domain.store.dto.businesscert.nts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NTSApiResponse {
    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("request_cnt")
    private int requestCount;

    @JsonProperty("valid_cnt")
    private int validCount;

    private List<NTSApiResponseData> data;

    @Data
    public static class NTSApiResponseData {
        @JsonProperty("b_no")
        private String businessNumber;

        @JsonProperty("valid")
        private String valid;

        @JsonProperty("valid_msg")
        private String validMessage;

        @JsonProperty("request_param")
        private RequestParam requestParam;

        @JsonProperty("status")
        private Status status;

        @Data
        public static class RequestParam {
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

        @Data
        public static class Status {
            @JsonProperty("b_no")
            private String businessNumber;

            @JsonProperty("b_stt")
            private String businessStatus;

            @JsonProperty("b_stt_cd")
            private String businessStatusCode;

            @JsonProperty("tax_type")
            private String taxType;

            @JsonProperty("tax_type_cd")
            private String taxTypeCode;

            @JsonProperty("end_dt")
            private String endDate;

            @JsonProperty("utcc_yn")
            private String utccYn;

            @JsonProperty("tax_type_change_dt")
            private String taxTypeChangeDate;

            @JsonProperty("invoice_apply_dt")
            private String invoiceApplyDate;

            @JsonProperty("rbf_tax_type")
            private String rbfTaxType;

            @JsonProperty("rbf_tax_type_cd")
            private String rbfTaxTypeCode;
        }
    }
}
