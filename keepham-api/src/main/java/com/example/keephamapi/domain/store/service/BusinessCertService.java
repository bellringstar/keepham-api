package com.example.keephamapi.domain.store.service;

import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.domain.store.dto.businesscert.BusinessCertRequest;
import com.example.keephamapi.domain.store.dto.businesscert.nts.NTSApiResponse;
import com.example.keephamapi.domain.store.entity.BusinessCert;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.repository.BusinessCertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BusinessCertService {

    private final static String VALID = "01";
    private final static String INVALID = "02";

    private final BusinessCertRepository businessCertRepository;
    private final NTSClient ntsClient;

    public boolean verifyBusinessCertInfo(BusinessCertRequest request) {
        // 국세청 api로 요청
        NTSApiResponse ntsApiResponse = ntsClient.validateBusinessInfo(request.getBusinessNumber(),
                request.getOpeningDate(),
                request.getRepresentativeName());
        // 유효하지 않으면 예외 발생
        String valid = ntsApiResponse.getData().get(0).getValid();
        if (valid.equals(VALID)) {
            return true;
        }
        return false;
    }

    public BusinessCert saveBusinessCertInfo(BusinessCertRequest request, Store store) {
        BusinessCert businessCert = BusinessCert.builder()
                .businessNumber(request.getBusinessNumber())
                .openingDate(request.getOpeningDate())
                .representativeName(request.getRepresentativeName())
                .store(store)
                .build();

        return businessCertRepository.save(businessCert);
    }
}
