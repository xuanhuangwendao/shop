package com.xing.shop.controller;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Summary;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.ItemListResponse;
import com.xing.shop.service.DetailService;
import com.xing.shop.service.OrderService;
import com.xing.shop.service.RecommendService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

    @Resource
    private OrderService orderService;

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

    @RequestMapping("/addCart")
    public Result<Boolean> addCart(@RequestParam Long itemId, @RequestParam Integer num) {
        Result<Boolean> result = orderService.createOrder(1, itemId, num);
        return result;
    }

    @RequestMapping("/getOrder")
    public Result<CartResponse> getOrder(@RequestParam Integer status) {
        Result<CartResponse> result = orderService.getOrderListByStatus(status);
        return result;
    }

    @RequestMapping("/updateOrder")
    public Result<Boolean> updateOrder(@RequestParam Long orderId, @RequestParam String operation, @RequestParam String content) {
        if (StringUtils.equals(operation, "update")) {
            int num = NumberUtils.toInt(content);
            Result<Boolean> result = orderService.update(orderId, num);
            return result;
        } else {
            return Result.fail(ResultCode.MODIFY_ORDER_FAIL);
        }
    }

}
