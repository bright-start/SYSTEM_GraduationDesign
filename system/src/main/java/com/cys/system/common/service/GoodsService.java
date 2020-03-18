package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Goods;
import com.cys.system.common.pojo.Product;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    Result listGoods(Integer page, Integer rows, Goods goods);

    Result findGoodsById(Integer id);

    Result examine(Integer id, Integer status);

    Result examineSelected(Integer[] ids, Integer status);

    Result addGoods(Goods goods, Integer shopId);

    Result updateGoodsByGoods(Goods goods);

    void deleteGoodsById(Integer id);

    Result updateStatus(Integer[] ids, Integer status);

    Product saleProduct();

    Result transactionAnalysis();

    List<Map> listDeleteGoods();

    Result findGoodGoods();

    Result recomment(Integer id,Integer status);
}
