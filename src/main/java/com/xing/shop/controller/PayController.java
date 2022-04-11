package com.xing.shop.controller;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Order;
import com.xing.shop.service.PayService;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/payOrder")
    public Result<List<Order>> payOrder() {
        Result<List<Order>> listResult = payService.payOrder(List.of(1L,2L,3L));
        return listResult;
    }

}
