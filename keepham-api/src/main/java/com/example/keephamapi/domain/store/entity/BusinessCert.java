package com.example.keephamapi.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessCert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_fee_id")
    private Long id;

    private String businessNumber; //숫자로 이루어진 10자리값
    private String representativeName; //외국인 사업자의 경우 영문명
    private LocalDate openingDate; //YYYYMMDD 포맷으로 국세청api로 요청해야 하는 값

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public BusinessCert(String businessNumber, String representativeName, LocalDate openingDate, Store store) {
        this.businessNumber = businessNumber;
        this.representativeName = representativeName;
        this.openingDate = openingDate;
        this.store = store;
    }
}
