package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.SearchEntity;
import com.cys.system.common.pojo.Goods;
import com.cys.system.common.service.GoodsSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods_engine")
public class GoodsEngineController {

    @Autowired
    private GoodsSummaryService goodsSummaryService;

    @GetMapping("/importGoods")
    public Result importGoods(){
        return goodsSummaryService.importGoodsToEngine();
    }

    @PostMapping(value = "/search",consumes = "application/json")
    public Result search(@RequestBody SearchEntity searchEntity){
        return goodsSummaryService.search(searchEntity);
    }
}
