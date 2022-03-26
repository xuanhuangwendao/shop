package com.xing.shop.pay;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Order;
import com.xing.shop.repository.OrderRepository;
import com.xing.shop.service.PayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/26 18:18
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public Result<List<Order>> payOrder(List<Long> orderIdList) {
        List<Order> orders = orderRepository.getAllByIdIn(orderIdList);

        return Result.success(orders);

    }
}
