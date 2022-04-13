package com.xing.shop.domain.response;

import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/13 23:01
 */
@Data
public class OrderResponse {

    public long orderId;

    public String title;

    public String picUrl;

    public double price;

    public String priceText;

    public int num;

}
