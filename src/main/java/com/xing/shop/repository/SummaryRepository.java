package com.xing.shop.repository;

import com.xing.shop.domain.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryRepository extends JpaRepository<Summary, Long> {
}