package com.xing.shop.domain.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/13 23:01
 */
@Data
public class CartResponse {

    private List<OrderResponse> cartItemList = new ArrayList<>();

}
