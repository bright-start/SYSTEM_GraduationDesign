package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class GoodsController {

    @Resource
    private SystemInterface systemInterface;

    @GetMapping("/get")
    public Result getGoodsById(Integer id) {
        return systemInterface.getGoodsById(id);
    }

    @GetMapping("/recomment")
    public Result recommnet(@RequestParam Integer id,@RequestParam Integer status){
        return systemInterface.recomment(id,status);
    }
}
