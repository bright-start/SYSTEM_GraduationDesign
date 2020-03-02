package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    List<Goods> listGoods(Goods goods);

    Goods findGoodsById(Integer goodsId);

    long count(Goods goods);

    int examine(@Param("id") Integer id, @Param("status") Integer status);
}
