package com.xing.shop.domain.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/13 22:27
 */
@Data
public class RecommendResponse {

    private List<SimpleItemResponse> itemList = new ArrayList<>();

}
