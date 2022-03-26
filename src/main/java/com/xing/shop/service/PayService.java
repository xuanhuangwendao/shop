package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Order;

import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/26 18:16
 */

public interface PayService {
    Result<List<Order>> payOrder(List<Long> orderIdList);

}
