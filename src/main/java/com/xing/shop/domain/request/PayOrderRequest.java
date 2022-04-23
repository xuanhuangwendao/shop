package com.xing.shop.domain.request;

import lombok.Data;

import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/23 20:44
 */
@Data
public class PayOrderRequest {
    public List<Long> orderList;
}
