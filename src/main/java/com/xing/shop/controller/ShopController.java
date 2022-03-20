package com.xing.shop.controller;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.ItemListResponse;
import com.xing.shop.service.RecommendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 13:05
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource
    private RecommendService recommendService;

    @RequestMapping("/recommend")
    public Result<ItemListResponse> recommend() {
        return recommendService.getHomeRecommendList();
    }
}
