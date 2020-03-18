package com.cys.system.common.config;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public final static String EXCHANGE = "GOOD_EXCHANGE";
    public final static String ROUTING_KEY = "shop.#";
//    public final static String PRODUCT_QUEUE = "product_message_queue";

    public final static String[] GOODS_STATUS_LIST = {"商品创建成功","商品等待审核","商品审核未通过","商品审核通过","商品已上架","商品无货","商品下架成功"};
}
