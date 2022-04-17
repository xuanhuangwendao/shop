package com.xing.shop.impl;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Context;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.entity.ItemBase;
import com.xing.shop.domain.entity.OrderInfo;
import com.xing.shop.domain.entity.Wallet;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.OrderResponse;
import com.xing.shop.domain.response.OrderUpdateResponse;
import com.xing.shop.repository.ItemBaseRepository;
import com.xing.shop.repository.OrderInfoRepository;
import com.xing.shop.repository.WalletRepository;
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

    @Resource
    private WalletRepository walletRepository;

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
        List<OrderInfo> orderInfoList;
        if (status == 0) {
            orderInfoList = orderInfoRepository.getAllByBuyerIdEquals(context.getUserId());

        } else {
            orderInfoList = orderInfoRepository.getAllByStatusEqualsAndBuyerIdEquals(status, context.getUserId());
        }
        for (OrderInfo orderInfo : orderInfoList) {
            if (orderInfo.getStatus() == -1) {
                continue;
            }
            OrderResponse orderResponse = new OrderResponse();
            ItemBase item = itemBaseRepository.getById(orderInfo.getItemId());
            orderResponse.setOrderId(orderInfo.getId());
            orderResponse.setPrice(orderInfo.getAmount().doubleValue());
            orderResponse.setNum(orderInfo.getItemNum());
            orderResponse.setTitle(item.getTitle());
            orderResponse.setPicUrl(item.getPicUrl());
            orderResponse.setPriceText(orderInfo.getAmount().toPlainString());
            switch (orderInfo.getStatus()) {
                case 1 -> orderResponse.setStatus("待付款");
                case 2 -> orderResponse.setStatus("已付款");
                case 4 -> orderResponse.setStatus("已完成");
            }
            orderResponse.setCreateTime(orderInfo.getGmtCreate().toString());
            if (orderInfo.getStatus() == 2 || orderInfo.getStatus() == 4 ) {
                orderResponse.setPayTime(orderInfo.getGmtModified().toString());

            }
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

    /**
     * 支付订单
     * 1、验证钱包
     * 2、验证库存
     * 3、扣减
     * 4、更新订单
     * @param orderIds
     * @return
     */
    @Override
    public Result<OrderUpdateResponse> payOrder(List<Long> orderIds) {
        Long userId = ThreadUtils.context.get().getUserId();

        List<OrderInfo> orderList = orderInfoRepository.findAllById(orderIds);
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderInfo orderInfo : orderList) {
            sum = sum.add(orderInfo.getAmount());

        }
        Wallet wallet = walletRepository.getById(userId);
        if (wallet.getBalance().compareTo(sum) < 0) {
            return Result.fail(ResultCode.BALANCE_LOSS);
        }
        wallet.setBalance(wallet.getBalance().subtract(sum));
        walletRepository.save(wallet);

        for (OrderInfo orderInfo : orderList) {
            ItemBase item = itemBaseRepository.getById(orderInfo.getItemId());
            if (item.getStock() < orderInfo.getItemNum()) {
                return Result.fail(ResultCode.STOCK_NOT_ENOUGH);
            }
            item.setStock(item.getStock() - orderInfo.getItemNum());
            orderInfo.setStatus(2);
            orderInfoRepository.save(orderInfo);
            itemBaseRepository.save(item);
        }
        OrderUpdateResponse response = new OrderUpdateResponse();
        return Result.success(response);
    }

}
