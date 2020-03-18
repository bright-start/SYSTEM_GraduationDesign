package com.cys.system.common.common.mq;

import com.cys.system.common.config.Config;
import com.cys.system.common.mapper.GoodsEngineMapper;
import com.cys.system.common.mapper.GoodsMapper;
import com.cys.system.common.mapper.GoodsSummaryMapper;
import com.cys.system.common.mapper.MsgMapper;
import com.cys.system.common.pojo.GoodsSummary;
import com.cys.system.common.pojo.Msg;
import com.cys.system.common.util.TimeConverter;
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
    final static Gson gson = new Gson();

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

        Map map = gson.fromJson(msg,Map.class);
        Integer id = (Integer) map.get("id");
        Integer status = (Integer) map.get("status");

        GoodsSummary goodsSummary = goodsSummaryMapper.findGoodsSummaryById(id);
        goodsEngineMapper.save(goodsSummary);

        Map info = goodsMapper.findGoodsNameAndShopIdById(id);

        String message = "[" + (String)info.get("goods_name") + "]" + Config.GOODS_STATUS_LIST[status];
        log.info(message);

        Msg msg1 = new Msg();
        msg1.setMessage(message);
        msg1.setMsgType(3);
        msg1.setMsgBelong((Integer) info.get("shop_id"));
        msg1.setCreateTime(TimeConverter.DateToString(new Date()));
        msg1.setRead(0);

        msgMapper.storeMessage(msg1);
    }

}
