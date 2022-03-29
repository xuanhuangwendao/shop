package com.xing.shop.domain.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/25 23:02
 */
@Data
public class CartResponse {

    List<CartItem> cartItemList = new ArrayList<>();

    private Double totalAmount;

    private String totalAmountText;

    @Data
    public static class CartItem {

        private Long orderId;

        private String picUrl;

        private String title;

        private Double price;

        private String priceText;

        private Integer num;
    }

}
