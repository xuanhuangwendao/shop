package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.ItemListResponse;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 12:51
 */
public interface RecommendService {

    Result<ItemListResponse> getHomeRecommendList();

}
