package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.OrderResponse;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/23 21:04
 */
public interface OrderService {

    Result<CartResponse> getOrderListByStatus(int status);

    Result<Boolean> createOrder(long userId, long itemId, int num);

}
