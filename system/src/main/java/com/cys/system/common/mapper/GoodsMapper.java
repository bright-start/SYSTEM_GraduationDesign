package com.cys.system.common.mapper;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Goods;
import com.cys.system.common.pojo.GoodsSummary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {
    List<Goods> listGoods(Goods goods);

    Goods findGoodsById(Integer id);

    long count(Goods goods);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    void addGoods(Goods goods);

    void deleteGoodsById(Integer id);

    void updateGoods(Goods goods);

    Integer getStatusById(Integer id);

    List<Map> listDeleteGoods();

    Map findGoodsNameAndShopIdById(Integer id);

    List<Map> findGoodGoods();

    @Update("update goods set goods_recomment = goods_recomment+#{status} where goods_id =#{id}")
    void recomment(@Param("id") Integer id, @Param("status") Integer status);
}
