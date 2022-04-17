package com.xing.shop.domain.request;

import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/17 21:33
 */
@Data
public class CreateShopRequest {

    public Long goodsId;

    public Integer num;

    public String price;

    public String desc;
}
