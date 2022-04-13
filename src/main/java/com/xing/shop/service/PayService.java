package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.OrderUpdateResponse;

import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/26 18:16
 */

public interface PayService {

    Result<OrderUpdateResponse> createOrder(long itemId, int num);

    Result<CartResponse> getOrderListByStatus(int status);

    Result<OrderUpdateResponse> update(long orderId, int num);



}
