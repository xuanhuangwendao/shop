package com.xing.shop.repository;

import com.xing.shop.domain.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

    List<OrderInfo> getAllByStatusEqualsAndBuyerIdEquals(int status, long buyerId);

    List<OrderInfo> getAllByBuyerIdEquals(long buyerId);

    List<OrderInfo> getAllByIdIn(List<Long> idList);
}