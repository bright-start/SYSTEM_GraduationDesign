package com.cys.system.admin.refresh;

import com.cys.system.common.config.RefreshCenter;
import com.cys.system.common.pojo.TimeTask;
import com.cys.system.common.service.GoodsService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 下架商品定时删除插件
 */
@Component
public class GoodsRefresh extends RefreshCenter {

    @Autowired
    private GoodsService goodsService;

    /**
     * 删除下架时间超过一个月的商品
     */
    @Override
    protected void timeTask() {
        List<Map> listDeleteGoods = goodsService.listDeleteGoods();
        if (listDeleteGoods != null && !listDeleteGoods.isEmpty()) {
            for (Map deleteGoods : listDeleteGoods) {
                if(deleteGoods!= null && !deleteGoods.isEmpty()){
                    String lowerSelfTime = (String)deleteGoods.get("goods_lowerselftime");
                    Integer deleteGoodsId =  (Integer) deleteGoods.get("goods_id");
                    try {
                        long monthTime = 15 * 24 * 60 * 60 * 1000;
                        if (((new Date()).getTime()) - (TimeConverter.StringToDate(lowerSelfTime).getTime()) >= monthTime) {
                            goodsService.deleteGoodsById(deleteGoodsId);
                        }
                    } catch (Exception e) {
//                         日志记录
                    }
                }
            }
        }
    }

    @Override
    protected int getTypeId() {
        return 3;
    }
}
