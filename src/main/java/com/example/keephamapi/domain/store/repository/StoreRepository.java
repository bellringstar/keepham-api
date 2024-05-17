package com.example.keephamapi.domain.store.repository;

import com.example.keephamapi.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
