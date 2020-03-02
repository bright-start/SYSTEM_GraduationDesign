package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Goods;
import org.springframework.web.bind.annotation.RequestBody;

public interface GoodsService {
    Result listGoods(Integer page, Integer rows, Goods goods);

    Result findGoodsById(Integer id);

    Result examine(Integer id, Integer status);

    Result examineSelected(Integer[] ids, Integer status);
}
