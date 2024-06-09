package com.example.keephamapi.domain.box.repository;

import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxGroupRepository extends JpaRepository<BoxGroup, Long> {

    Optional<BoxGroup> findBoxGroupByIdAndStatus(Long boxGroupId, BoxGroupStatus status);
}
