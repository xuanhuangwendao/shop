package com.xing.shop.domain.response;

import com.xing.shop.domain.model.Summary;
import lombok.Data;

import java.util.List;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 12:52
 */
@Data
public class ItemListResponse {

    public List<Summary> itemList;
}
