package com.xing.shop.shop;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Summary;
import com.xing.shop.domain.response.ItemListResponse;
import com.xing.shop.repository.SummaryRepository;
import com.xing.shop.service.RecommendService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 12:54
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    SummaryRepository summaryRepository;


    @Override
    public Result<ItemListResponse> getHomeRecommendList() {
        List<Summary> itemList = summaryRepository.findAll();
        if (CollectionUtils.isEmpty(itemList)) {
            return Result.fail(ResultCode.EMPTY_RESULT);
        }
        ItemListResponse response = new ItemListResponse();
        response.setItemList(itemList);
        return Result.success(response);
    }
}
