package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Summary;

import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 23:57
 */
public interface DetailService {

    Result<Summary> itemDetail(Long id);
}
