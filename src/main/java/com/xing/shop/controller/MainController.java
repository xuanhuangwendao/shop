package com.xing.shop.controller;

import com.xing.shop.domain.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 0:44
 */
@RestController
public class MainController {

    @RequestMapping("/index")
    public Result<String> index() {
        return Result.success("hello");
    }


}
