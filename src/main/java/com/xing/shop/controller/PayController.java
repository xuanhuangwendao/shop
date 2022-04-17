package com.xing.shop.controller;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.OrderUpdateResponse;
import com.xing.shop.service.OrderService;
import com.xing.shop.service.PayService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/26 18:10
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayService payService;


    @RequestMapping("/addCart")
    public Result<OrderUpdateResponse> addCart(@RequestParam Long itemId, @RequestParam Integer num) {
        Result<OrderUpdateResponse> result = payService.createOrder(itemId, num);
        return result;
    }

    @RequestMapping("/getOrder")
    public Result<CartResponse> getOrder(@RequestParam Integer status) {
        Result<CartResponse> result = payService.getOrderListByStatus(status);
        return result;
    }

    @RequestMapping("/updateOrder")
    public Result<OrderUpdateResponse> updateOrder(@RequestParam Long orderId, @RequestParam String operation, @RequestParam String content) {
        if (StringUtils.equals(operation, "update")) {
            int num = NumberUtils.toInt(content);
            Result<OrderUpdateResponse> result = payService.update(orderId, num);
            return result;
        } else {
            return Result.fail(ResultCode.MODIFY_ORDER_FAIL);
        }
    }

    @RequestMapping("/payOrder")
    public Result<OrderUpdateResponse> payOrder(@RequestParam List<Long> orderList) {
            return payService.payOrder(orderList);
    }
}
