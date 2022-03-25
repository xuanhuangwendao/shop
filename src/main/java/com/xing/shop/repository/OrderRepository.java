package com.xing.shop.repository;

import com.xing.shop.domain.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByTypeEqualsAndBuyerIdEquals(int type, long buyerId);

}