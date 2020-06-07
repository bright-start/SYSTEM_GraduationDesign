package com.cys.system.common.common.mq;

import com.cys.system.common.config.Config;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.GoodsEngineMapper;
import com.cys.system.common.mapper.GoodsMapper;
import com.cys.system.common.mapper.GoodsSummaryMapper;
import com.cys.system.common.mapper.MsgMapper;
import com.cys.system.common.pojo.GoodsSummary;
import com.cys.system.common.pojo.Msg;
import com.cys.system.common.util.TimeConverter;
import com.cys.system.common.util.TimeFormat;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
public class ShopMessageReceiver {

    final static Logger log = LoggerFactory.getLogger(ShopMessageReceiver.class);

    @Resource
    GoodsSummaryMapper goodsSummaryMapper;

    @Resource
    GoodsEngineMapper goodsEngineMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    MsgMapper msgMapper;

    @RabbitListener(queues = "product_message_queue")
    public void process(String msg){

        Map map = OnlyOneClassConfig.gson.fromJson(msg,Map.class);
        Integer id =  ((Double) map.get("id")).intValue();
        Integer status = ((Double) map.get("status")).intValue();

        //上架
        if(status == 4) {
            GoodsSummary goodsSummary = goodsSummaryMapper.findGoodsSummaryById(id);
            goodsEngineMapper.save(goodsSummary);
        }
        //下架
        if(status == 6){
            goodsEngineMapper.deleteById(id);
        }

        Map info = goodsMapper.findGoodsNameAndShopIdById(id);


        String message = "[" + (String)info.get("goods_name") + "]" + Config.GOODS_STATUS_LIST[status];
        log.info(message);

        Msg msg1 = new Msg();
        msg1.setMessage(message);
        msg1.setMsgType(3);
        msg1.setMsgBelong((Integer) info.get("shop_id"));
        msg1.setCreateTime(TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S));
        msg1.setRead(0);

        msgMapper.storeMessage(msg1);
    }

}
