package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private SystemInterface indexInterface;

    @GetMapping("/findAreaList")
    public Result findAreaList(){
        return indexInterface.findAreaList();
    }

    @GetMapping("/findGoodGoods")
    public Result findGoodGoods(){
        return indexInterface.findGoodGoods();
    }
}
