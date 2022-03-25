package com.xing.shop.domain.response;

import com.xing.shop.domain.model.Order;
import lombok.Data;

import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/23 21:09
 */
@Data
public class OrderResponse {

    List<Order> orderList;
}
