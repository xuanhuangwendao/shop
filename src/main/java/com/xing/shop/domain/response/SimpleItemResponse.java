package com.xing.shop.domain.response;

import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/13 22:31
 */
@Data
public class SimpleItemResponse {

    private Long id;

    private String title;

    private String price;

    private String sellerName;

    private String picUrl;
}
