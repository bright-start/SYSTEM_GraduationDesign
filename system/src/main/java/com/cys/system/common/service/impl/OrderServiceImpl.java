package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.UpOrder;
import com.cys.system.common.mapper.CartItemMapper;
import com.cys.system.common.mapper.OrderItemMapper;
import com.cys.system.common.mapper.OrderMapper;
import com.cys.system.common.pojo.CartItem;
import com.cys.system.common.pojo.Order;
import com.cys.system.common.pojo.OrderItem;
import com.cys.system.common.service.OrderService;
import com.cys.system.common.util.TimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = {Exception.class})
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private CartItemMapper cartItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();

    @Override
    public Result buildOrder(Integer[] ids) {
        Map<Integer, Map<Order, List<OrderItem>>> orderMap = new LinkedHashMap<>();
        for (Integer id : ids) {
            CartItem cartItem = cartItemMapper.getCartItemByItemId(id);
            Integer shopId = cartItem.getShopId();
            if (orderMap.containsKey(shopId)) {
                Map<Order, List<OrderItem>> orderListMap = orderMap.get(shopId);

                orderMap.put(shopId, addToOrderMap(orderListMap, cartItem));
            } else {
                Map<Order, List<OrderItem>> orderListMap = new LinkedHashMap<>();

                orderMap.put(shopId, addToOrderMap(orderListMap, cartItem));
            }
        }

        String orderCode = "code_" + UUID.randomUUID().toString().replace("-", "");
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.opsForValue().set(orderCode, gson.toJson(orderMap));
        redisTemplate.expire(orderCode, 30, TimeUnit.MINUTES);

        return new Result().success(orderCode);
    }


    private Map<Order, List<OrderItem>> addToOrderMap(Map<Order, List<OrderItem>> orderListMap, CartItem cartItem) {

        Random random = new Random();
        int num = random.nextInt(100 - 10) + 10;

        String orderId = new Date().getTime()
                + TimeConverter.DateToString(new Date())
                .replace("-", "")
                .replace(" ", "")
                .replace(":", "")
                + num;

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderId(orderId);
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setProductImage(cartItem.getProductImage());
        orderItem.setProductName(cartItem.getProductName());
        orderItem.setProductPrice(cartItem.getProductPrice());
        orderItem.setProductCount(cartItem.getProductCount());
        orderItem.setAreaName(cartItem.getAreaName());
        orderItem.setShopId(cartItem.getShopId());
        orderItem.setShopName(cartItem.getShopName());
        orderItem.setDiscount(cartItem.getDiscount());
        orderItem.setPostage(cartItem.getPostage());


        if (orderListMap.size() == 0) {
            Order order = new Order(orderId, cartItem.getShopId());
            order.setShopName(orderItem.getShopName());
            order.setOrderCount(1);
            order.setTotalPrice(orderItem.getProductPrice() * orderItem.getProductCount());

            List<OrderItem> orderItems = new LinkedList<>();
            orderItems.add(orderItem);
            orderListMap.put(order, orderItems);
        } else {
            for (Map.Entry<Order, List<OrderItem>> orderListEntry : orderListMap.entrySet()) {
                orderListEntry.getKey().setOrderCount(orderListEntry.getKey().getOrderCount() + 1);
                orderListEntry.getKey().setTotalPrice(orderListEntry.getKey().getTotalPrice() + orderItem.getProductPrice() * orderItem.getProductCount());
                orderListEntry.getValue().add(orderItem);
            }
        }

        return orderListMap;
    }


    @Transactional(readOnly = true)
    @Override
    public Result lookNoPay(List<String> noPayList) {
        List<Map<Integer, Map<Order, List<OrderItem>>>> noPayOrderList = new LinkedList<>();
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        for (String noPayCode : noPayList) {
            String noPayOrderJson = (String) redisTemplate.opsForValue().get(noPayCode);
            if (noPayOrderJson != null) {
                Map<Integer, Map<Order, List<OrderItem>>> noPayOrderMap = (Map<Integer, Map<Order, List<OrderItem>>>) gson.fromJson(noPayOrderJson, Map.class);
                noPayOrderList.add(noPayOrderMap);
            }

        }
        return new Result().success(noPayOrderList);
    }

    @Transactional(readOnly = true)
    @Override
    public Result lookPay(Integer userId, String orderId) {
        List<Order> orders = orderMapper.lookPay(userId, orderId);
        return new Result().success();
    }

    @Override
    public Result pay(UpOrder upOrder) {

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        List<String> noPayList = gson.fromJson(upOrder.getNoPayList(), List.class);
        for (String noPayCode : noPayList) {

            String noPayOrderJson = (String) redisTemplate.opsForValue().get(noPayCode);
            if (noPayOrderJson != null) {
                Type type = new TypeToken<Map<Integer, Map<Order, List<OrderItem>>>>(){ }.getType();
                Map<Integer, Map<Order, List<OrderItem>>> noPayOrderMap = gson.fromJson(noPayOrderJson, type);
                for (Map.Entry<Integer, Map<Order, List<OrderItem>>> integerMapEntry : noPayOrderMap.entrySet()) {

                    Map<Order, List<OrderItem>> orderListMap = integerMapEntry.getValue();
                    for (Map.Entry<Order, List<OrderItem>> orderListEntry : orderListMap.entrySet()) {
                        Order order = orderListEntry.getKey();
                        List<OrderItem> orderItemList = orderListEntry.getValue();
                        for (OrderItem orderItem : orderItemList) {
                            orderItemMapper.createOrderItem(orderItem);
                        }
                        order.setCreateTime(TimeConverter.DateToString(new Date()));
                        order.setOrderCount(orderItemList.size());
                        order.setCreator(upOrder.getCreator());
                        order.setPhone(upOrder.getPhone());
                        order.setAddress(upOrder.getAddress());
                        order.setPayType(upOrder.getPayType());
                        orderMapper.createOrder(order);
                    }
                }
            }
            redisTemplate.expire(noPayCode, 0, TimeUnit.SECONDS);
        }

        //支付  支付失败则不生成最终订单

        return new Result().success(200, "付款成功");
    }
}
