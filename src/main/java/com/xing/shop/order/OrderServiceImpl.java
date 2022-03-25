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
            for (Order order : orderList) {
                CartResponse.CartItem item = new CartResponse.CartItem();
                item.setPrice(order.getPrice().doubleValue());
                item.setPriceText("￥" + order.getPrice());
                item.setNum(order.getItemNum());
                item.setTitle(order.getItemTitle());
                item.setOrderId(order.getId());
                cartItemList.add(item);
            }
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
}
