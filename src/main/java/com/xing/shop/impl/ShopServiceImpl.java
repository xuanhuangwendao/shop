package com.xing.shop.impl;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.entity.Good;
import com.xing.shop.domain.entity.ItemBase;
import com.xing.shop.domain.entity.UserInfo;
import com.xing.shop.domain.request.CreateShopRequest;
import com.xing.shop.domain.response.CreateShopResponse;
import com.xing.shop.domain.response.DetailResponse;
import com.xing.shop.domain.response.RecommendResponse;
import com.xing.shop.domain.response.SimpleItemResponse;
import com.xing.shop.repository.GoodRepository;
import com.xing.shop.repository.ItemBaseRepository;
import com.xing.shop.repository.UserInfoRepository;
import com.xing.shop.service.ShopService;
import com.xing.shop.util.ThreadUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 12:54
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private ItemBaseRepository itemBaseRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private GoodRepository goodRepository;


    @Override
    public Result<RecommendResponse> getHomeRecommendList() {
        List<ItemBase> itemList = itemBaseRepository.findAll();
        if (CollectionUtils.isEmpty(itemList)) {
            return Result.fail(ResultCode.EMPTY_RESULT);
        }
        RecommendResponse response = new RecommendResponse();
        for (ItemBase base : itemList) {
            SimpleItemResponse item = new SimpleItemResponse();
            item.setId(base.getId());
            item.setPrice(base.getPrice().toPlainString());
            item.setTitle(base.getTitle());
            // TODO: 后续优化成批量
            UserInfo seller = userInfoRepository.getById(base.getSellerId());
            item.setSellerName(seller.getNickname());
            item.setPicUrl(base.getPicUrl());
            response.getItemList().add(item);
        }
        return Result.success(response);
    }

    @Override
    public Result<DetailResponse> itemDetail(Long id) {
        if (id == null) {
            return Result.fail(ResultCode.ITEM_NOT_FOUND);
        }
        ItemBase base = itemBaseRepository.getById(id);
        DetailResponse response = new DetailResponse();
        response.setId(base.getId());
        UserInfo seller = userInfoRepository.getById(base.getSellerId());
        response.setSellerName(seller.getNickname());
        response.setPrice(base.getPrice().toPlainString());
        response.setStock(base.getStock());
        response.setPicUrl(base.getPicUrl());
        response.setDesc(base.getDesc());
        response.setTitle(base.getTitle());
        return Result.success(response);
    }

    @Override
    public Result<RecommendResponse> getGoodsList() {
        List<Good> goodList = goodRepository.findAll();
        if (CollectionUtils.isEmpty(goodList)) {
            return Result.fail(ResultCode.EMPTY_RESULT);
        }
        RecommendResponse response = new RecommendResponse();
        for (Good base : goodList) {
            SimpleItemResponse item = new SimpleItemResponse();
            item.setId(base.getId());
            item.setTitle(base.getTitle());
            item.setPicUrl(base.getPicUrl());
            response.getItemList().add(item);
        }
        return Result.success(response);
    }

    @Override
    public Result<DetailResponse> getGoodsItem(Long id) {
        Good good = goodRepository.getById(id);

        DetailResponse response = new DetailResponse();
        response.setId(good.getId());
        response.setPicUrl(good.getPicUrl());
        response.setTitle(good.getTitle());
        return Result.success(response);
    }

    @Override
    public Result<CreateShopResponse> createShop(CreateShopRequest request) {
        Good good = goodRepository.getById(request.getGoodsId());

        ItemBase base = new ItemBase();
        base.setStock(request.getNum());
        base.setDesc(request.getDesc());
        base.setTitle(good.getTitle());
        base.setPicUrl(good.getPicUrl());
        double price = NumberUtils.toDouble(request.getPrice());
        base.setPrice(BigDecimal.valueOf(price));
        base.setCategory(good.getCategory());
        base.setGmtCreate(Instant.now());
        long sellerId = ThreadUtils.context.get().getUserId();
        base.setSellerId(sellerId);
        base.setGmtModified(Instant.now());
        itemBaseRepository.save(base);
        CreateShopResponse response = new CreateShopResponse();
        return Result.success(response);
    }
}
