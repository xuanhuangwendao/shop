package com.xing.shop.impl;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Context;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.entity.ItemBase;
import com.xing.shop.domain.entity.OrderInfo;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.OrderResponse;
import com.xing.shop.domain.response.OrderUpdateResponse;
import com.xing.shop.repository.ItemBaseRepository;
import com.xing.shop.repository.OrderInfoRepository;
import com.xing.shop.service.PayService;
import com.xing.shop.util.ThreadUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/26 18:18
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private OrderInfoRepository orderInfoRepository;

    @Resource
    private ItemBaseRepository itemBaseRepository;

    @Override
    public Result<OrderUpdateResponse> createOrder(long itemId, int num) {
        ItemBase item = itemBaseRepository.getById(itemId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus(1);
        orderInfo.setAmount(item.getPrice().multiply(BigDecimal.valueOf(num)));
        orderInfo.setItemId(itemId);
        orderInfo.setItemNum(num);
        Context context = ThreadUtils.context.get();
        orderInfo.setBuyerId(context.getUserId());
        orderInfo.setSellerId(item.getSellerId());
        String groupId = context.getUserId() + "-" + UUID.randomUUID().toString().replaceAll("-", "");
        orderInfo.setGroupId(groupId);
        orderInfo.setGmtCreate(Instant.now());
        orderInfo.setGmtModified(Instant.now());
        orderInfoRepository.save(orderInfo);
        OrderUpdateResponse response = new OrderUpdateResponse();
        return Result.success(response);
    }

    @Override
    public Result<CartResponse> getOrderListByStatus(int status) {
        CartResponse cartResponse = new CartResponse();

        Context context = ThreadUtils.context.get();
        List<OrderInfo> orderInfoList = orderInfoRepository.getAllByStatusEqualsAndBuyerIdEquals(status, context.getUserId());
        for (OrderInfo orderInfo : orderInfoList) {
            OrderResponse orderResponse = new OrderResponse();
            ItemBase item = itemBaseRepository.getById(orderInfo.getItemId());
            orderResponse.setOrderId(orderInfo.getId());
            orderResponse.setPrice(orderInfo.getAmount().doubleValue());
            orderResponse.setNum(orderInfo.getItemNum());
            orderResponse.setTitle(item.getTitle());
            orderResponse.setPicUrl(item.getPicUrl());
            orderResponse.setPriceText(orderInfo.getAmount().toPlainString());
            cartResponse.getCartItemList().add(orderResponse);
        }
        return Result.success(cartResponse);
    }


    @Override
    public Result<OrderUpdateResponse> update(long orderId, int num) {
        OrderInfo order = orderInfoRepository.getById(orderId);
        OrderUpdateResponse response = new OrderUpdateResponse();
        int itemNum = order.getItemNum() + num;
        if (itemNum < 0) {
            return Result.fail(ResultCode.MODIFY_ORDER_FAIL);
        } else if (itemNum == 0) {
            order.setStatus(-1);
            order.setItemNum(0);
            order.setAmount(BigDecimal.ZERO);
            order.setGmtModified(Instant.now());
            orderInfoRepository.save(order);
            return Result.success(response);
        } else {
            Long itemId = order.getItemId();
            ItemBase item = itemBaseRepository.getById(itemId);
            if (item.getStock() < itemNum) {
                return Result.fail(ResultCode.STOCK_NOT_ENOUGH);
            }
            order.setItemNum(itemNum);
            order.setAmount(item.getPrice().multiply(BigDecimal.valueOf(itemNum)));
            order.setGmtModified(Instant.now());
            orderInfoRepository.save(order);
            return Result.success(response);
        }
    }

}
