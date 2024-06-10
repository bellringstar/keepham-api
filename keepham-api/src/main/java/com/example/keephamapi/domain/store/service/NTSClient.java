package com.example.keephamapi.domain.store.service;

import com.example.keephamapi.domain.store.dto.businesscert.nts.NTSApiRequest;
import com.example.keephamapi.domain.store.dto.businesscert.nts.NTSApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class NTSClient {

    //TODO: 예외 메시지 처리 필요.

    private final RestTemplate restTemplate;

    @Value("${nts.api.url}")
    private String ntsApiUrl;

    @Value("${nts.api.key}")
    private String ntsApiKey;

    public NTSApiResponse validateBusinessInfo(String businessNumber, String startDate, String representativeName) {
        String url = UriComponentsBuilder.fromHttpUrl(ntsApiUrl + "/validate")
                .queryParam("serviceKey", ntsApiKey)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        NTSApiRequest.Business business = NTSApiRequest.Business.builder()
                .businessNumber(businessNumber)
                .startDate(startDate)
                .representativeName(representativeName)
                .representativeName2("")
                .businessAddress("")
                .businessName("")
                .corporateNumber("")
                .businessType("")
                .businessSector("")
                .build();

        NTSApiRequest requestBody = new NTSApiRequest(List.of(business));

        HttpEntity<NTSApiRequest> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<NTSApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                NTSApiResponse.class
        );

        return response.getBody();
    }
}
