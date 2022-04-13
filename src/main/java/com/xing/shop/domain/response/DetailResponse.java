package com.xing.shop.domain.response;

import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/13 22:36
 */
@Data
public class DetailResponse {

    private Long id;

    private String title;

    private String sellerName;

    private String price;

    private Integer stock;

    private String desc;

    private String picUrl;

}
