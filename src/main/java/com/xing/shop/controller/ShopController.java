package com.xing.shop.controller;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Summary;
import com.xing.shop.domain.response.ItemListResponse;
import com.xing.shop.service.DetailService;
import com.xing.shop.service.RecommendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private DetailService detailService;

    @RequestMapping("/recommend")
    public Result<ItemListResponse> recommend() {
        Result<ItemListResponse> result = recommendService.getHomeRecommendList();
        return result;
    }
    @RequestMapping("/detail")
    public Result<Summary> detail(@RequestParam Long id) {
        Result<Summary> result = detailService.itemDetail(id);
        return result;
    }

}
