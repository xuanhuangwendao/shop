package com.xing.shop.order;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Order;
import com.xing.shop.domain.model.Summary;
import com.xing.shop.domain.response.CartResponse;
import com.xing.shop.domain.response.OrderResponse;
import com.xing.shop.repository.OrderRepository;
import com.xing.shop.repository.SummaryRepository;
import com.xing.shop.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/23 21:11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private SummaryRepository summaryRepository;

    @Override
    public Result<CartResponse> getOrderListByStatus(int status) {


        CartResponse cartResponse = new CartResponse();
        List<CartResponse.CartItem> cartItemList = cartResponse.getCartItemList();
        if (status == -1) {

        } else {
            List<Order> orderList = orderRepository.getAllByTypeEqualsAndBuyerIdEquals(status, 1L);
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (Order order : orderList) {
                Optional<Summary> summary = summaryRepository.findById(order.getItemId());
                if (summary.isEmpty()) {
                    continue;
                }
                CartResponse.CartItem item = new CartResponse.CartItem();
                item.setPrice(order.getPrice().doubleValue());
                item.setPriceText("￥" + order.getPrice());
                item.setNum(order.getItemNum());
                item.setTitle(order.getItemTitle());
                item.setPicUrl(summary.get().getPicUrl());
                item.setOrderId(order.getId());
                cartItemList.add(item);
                totalAmount = totalAmount.add(order.getPrice().multiply(BigDecimal.valueOf(order.getItemNum())));
            }
            cartResponse.setTotalAmount(totalAmount.doubleValue());
            cartResponse.setTotalAmountText("￥" + totalAmount);
        }
        return Result.success(cartResponse);
    }

    @Override
    public Result<Boolean> createOrder(long userId, long itemId, int num) {
        Optional<Summary> item = summaryRepository.findById(itemId);
        if (item.isEmpty()) {
            return Result.fail(ResultCode.ITEM_NOT_FOUND);
        }
        Order order = new Order();
        order.setType(1);
        order.setItemId(itemId);
        order.setAmount(item.get().getPrice().multiply(BigDecimal.valueOf(num)));
        order.setItemNum(num);
        order.setGmtCreate(Instant.now());
        order.setGmtModified(Instant.now());
        order.setBuyerId(1L);
        order.setSellerId(item.get().getSellerId());
        order.setPrice(item.get().getPrice());
        order.setItemTitle(item.get().getTitle());
        orderRepository.save(order);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> update(long orderId, int num) {
        Order order = orderRepository.getById(orderId);
        int itemNum = order.getItemNum() + num;
        if (itemNum < 0) {
            return Result.fail(ResultCode.MODIFY_ORDER_FAIL);
        } else if (itemNum == 0) {
            order.setType(-2);
            order.setItemNum(0);
            order.setAmount(BigDecimal.ZERO);
            order.setGmtModified(Instant.now());
            orderRepository.save(order);
            return Result.success(true);
        } else {
            Long itemId = order.getItemId();
            Summary item = summaryRepository.getById(itemId);
            if (item.getStock().compareTo(BigDecimal.valueOf(itemNum)) < 0) {
                return Result.fail(ResultCode.STOCK_NOT_ENOUGH);
            }
            order.setItemNum(itemNum);
            order.setAmount(order.getPrice().multiply(order.getPrice()));
            order.setGmtModified(Instant.now());
            orderRepository.save(order);
            return Result.success(true);
        }
    }
}
