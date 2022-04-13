package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.DetailResponse;
import com.xing.shop.domain.response.RecommendResponse;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 12:51
 */
public interface ShopService {

    Result<RecommendResponse> getHomeRecommendList();

    Result<DetailResponse> itemDetail(Long id);

}
