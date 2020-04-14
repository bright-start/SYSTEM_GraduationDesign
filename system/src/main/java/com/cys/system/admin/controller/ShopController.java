package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping("/apply/list")
    public Result listApplyShop(Integer page, Integer rows) throws InvalidRequestException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }

        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return shopService.listApplyShop(page, rows);
        } else {
            throw new InvalidRequestException("参数错误");
        }
    }

    @GetMapping("/examine")
    public Result examine(Integer[] ids,Integer status){
        return shopService.examine(ids,status);
    }

}
