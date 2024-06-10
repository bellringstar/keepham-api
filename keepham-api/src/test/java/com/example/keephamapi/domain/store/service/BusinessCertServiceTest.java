package com.example.keephamapi.domain.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.keephamapi.domain.store.dto.businesscert.BusinessCertRequest;
import com.example.keephamapi.domain.store.dto.businesscert.nts.NTSApiResponse;
import com.example.keephamapi.domain.store.entity.BusinessCert;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.repository.BusinessCertRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
@Rollback
class BusinessCertServiceTest {

    @Mock
    private BusinessCertRepository businessCertRepository;

    @Mock
    private NTSClient ntsClient;

    @InjectMocks
    private BusinessCertService businessCertService;

    private BusinessCertRequest validRequest;
    private BusinessCertRequest invalidRequest;
    private NTSApiResponse validResponse;
    private NTSApiResponse invalidResponse;
    private Store store;

    @BeforeEach
    void setUp() {
        validRequest = new BusinessCertRequest();
        validRequest.setBusinessNumber("0000000000");
        validRequest.setOpeningDate("20000101");
        validRequest.setRepresentativeName("홍길동");

        invalidRequest = new BusinessCertRequest();
        invalidRequest.setBusinessNumber("1111111111");
        invalidRequest.setOpeningDate("20010101");
        invalidRequest.setRepresentativeName("이몽룡");

        NTSApiResponse.NTSApiResponseData validData = new NTSApiResponse.NTSApiResponseData();
        validData.setValid("01");

        NTSApiResponse.NTSApiResponseData invalidData = new NTSApiResponse.NTSApiResponseData();
        invalidData.setValid("02");

        validResponse = new NTSApiResponse();
        validResponse.setData(List.of(validData));

        invalidResponse = new NTSApiResponse();
        invalidResponse.setData(List.of(invalidData));

        store = mock(Store.class);
    }

    @Test
    void verifyBusinessCertInfo_ValidInfo_shouldReturnTrue() {
        when(ntsClient.validateBusinessInfo(any(String.class), any(String.class), any(String.class)))
                .thenReturn(validResponse);

        boolean result = businessCertService.verifyBusinessCertInfo(validRequest);

        assertThat(result).isTrue();
    }

    @Test
    void verifyBusinessCertInfo_InvalidInfo_ShouldThrowApiException() {
        when(ntsClient.validateBusinessInfo(any(String.class), any(String.class), any(String.class)))
                .thenReturn(invalidResponse);

        boolean result = businessCertService.verifyBusinessCertInfo(invalidRequest);

        assertThat(result).isFalse();
    }

    @Test
    void saveBusinessCertInfo_ShouldReturnSavedBusinessCert() {
        BusinessCert expectedBusinessCert = BusinessCert.builder()
                .businessNumber(validRequest.getBusinessNumber())
                .openingDate(validRequest.getOpeningDate())
                .representativeName(validRequest.getRepresentativeName())
                .store(store)
                .build();

        when(businessCertRepository.save(any(BusinessCert.class))).thenReturn(expectedBusinessCert);

        BusinessCert result = businessCertService.saveBusinessCertInfo(validRequest, store);

        assertThat(result).isNotNull();
        assertThat(result.getBusinessNumber()).isEqualTo(validRequest.getBusinessNumber());
        assertThat(result.getOpeningDate()).isEqualTo(validRequest.getOpeningDate());
        assertThat(result.getRepresentativeName()).isEqualTo(validRequest.getRepresentativeName());
        assertThat(result.getStore()).isEqualTo(store);
    }
}
