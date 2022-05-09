package com.xing.shop.domain.request;

import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/5/8 18:47
 */
@Data
public class CreateGoodRequest {
    public String title;

    public String picUrl;

    public String categoryId;
}
