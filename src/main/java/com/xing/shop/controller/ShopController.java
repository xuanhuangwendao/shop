package com.xing.shop.controller;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.DetailResponse;
import com.xing.shop.domain.response.RecommendResponse;
import com.xing.shop.service.OrderService;
import com.xing.shop.service.ShopService;
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
    private ShopService shopService;

    @RequestMapping("/recommend")
    public Result<RecommendResponse> recommend() {
        Result<RecommendResponse> result = shopService.getHomeRecommendList();
        return result;
    }
    @RequestMapping("/detail")
    public Result<DetailResponse> detail(@RequestParam Long id) {
        Result<DetailResponse> result = shopService.itemDetail(id);
        return result;
    }


}
