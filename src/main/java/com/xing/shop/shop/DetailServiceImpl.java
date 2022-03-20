package com.xing.shop.shop;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Summary;
import com.xing.shop.repository.SummaryRepository;
import com.xing.shop.service.DetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 23:58
 */
@Service
public class DetailServiceImpl implements DetailService {

    @Resource
    private SummaryRepository summaryRepository;

    @Override
    public Result<Summary> itemDetail(Long id) {
        if (id == null) {
            return Result.fail(ResultCode.ITEM_NOT_FOUND);
        }
        Optional<Summary> summaryOptional = summaryRepository.findById(id);
        return summaryOptional.map(Result::success).orElseGet(() -> Result.fail(ResultCode.ITEM_NOT_FOUND));
    }
}
