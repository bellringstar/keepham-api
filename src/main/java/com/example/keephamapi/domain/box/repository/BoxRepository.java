package com.example.keephamapi.domain.box.repository;

import com.example.keephamapi.domain.box.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Long> {
}
