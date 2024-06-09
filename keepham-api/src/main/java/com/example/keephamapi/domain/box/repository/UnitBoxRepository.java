package com.example.keephamapi.domain.box.repository;

import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.UnitBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitBoxRepository extends JpaRepository<UnitBox, Long> {
}
