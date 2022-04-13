package com.xing.shop.repository;

import com.xing.shop.domain.entity.ItemBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBaseRepository extends JpaRepository<ItemBase, Long> {
}