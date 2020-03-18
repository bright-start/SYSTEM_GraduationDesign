package com.cys.system.common.common.mq;

import com.cys.system.common.config.Config;
import com.google.gson.Gson;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MsgSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    Gson gson = new Gson();

    public void sendToShop(Integer id, Integer status) {

        Map map = new HashMap();
        map.put("id", id);
        map.put("status", status);

        String msg = gson.toJson(map);
        this.amqpTemplate.convertAndSend(Config.EXCHANGE, "shop.product_message", msg);
    }
}
