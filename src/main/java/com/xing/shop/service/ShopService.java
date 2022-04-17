package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.request.CreateShopRequest;
import com.xing.shop.domain.response.CreateShopResponse;
import com.xing.shop.domain.response.DetailResponse;
import com.xing.shop.domain.response.RecommendResponse;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 12:51
 */
public interface ShopService {

    Result<RecommendResponse> getHomeRecommendList();

    Result<DetailResponse> itemDetail(Long id);

    Result<RecommendResponse> getGoodsList();

    Result<DetailResponse> getGoodsItem(Long id);

    Result<CreateShopResponse> createShop(CreateShopRequest request);


}
