package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.UpOrder;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.*;
import com.cys.system.common.pojo.*;
import com.cys.system.common.service.OrderService;
import com.cys.system.common.util.CodeConversionUtil;
import com.cys.system.common.util.TimeConverter;
import com.cys.system.common.util.TimeFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProductMapper productMapper;

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public Result buildOrder(Integer[] ids) throws NoSuchFieldException {
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

        // 是否处于可卖出状态检测
        // 。。。

        String json = OnlyOneClassConfig.gson.toJson(orderMap);

        byte[] bt = json.getBytes();
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchFieldException();
        }
        sha.update(bt);
        String digest = CodeConversionUtil.byteToHex(sha.digest());
        String orderCode = "code_" + digest;

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.opsForValue().set(orderCode, json);
        redisTemplate.expire(orderCode, 30, TimeUnit.MINUTES);

        return new Result().success(orderCode);
    }


    private Map<Order, List<OrderItem>> addToOrderMap(Map<Order, List<OrderItem>> orderListMap, CartItem cartItem) {

        Random random = new Random();
        int num = random.nextInt(100 - 10) + 10;

        String orderId = new Date().getTime()
                + TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S)
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
        Map<String, Map<Integer, Map<Order, List<OrderItem>>>> noPayOrderMapList = new HashMap<>();
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        for (String noPayCode : noPayList) {
            String noPayOrderJson = (String) redisTemplate.opsForValue().get(noPayCode);
            if (noPayOrderJson != null) {
                Map<Integer, Map<Order, List<OrderItem>>> noPayOrderMap = (Map<Integer, Map<Order, List<OrderItem>>>) OnlyOneClassConfig.gson.fromJson(noPayOrderJson, Map.class);
                noPayOrderMapList.put(noPayCode, noPayOrderMap);
            }

        }
        return new Result().success(noPayOrderMapList);
    }

    @Override
    public Result deleteOrder(String payToken) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        redisTemplate.expire(payToken, 0, TimeUnit.SECONDS);
        return new Result().success();
    }

    @Transactional(readOnly = true)
    @Override
    public Result lookPay(Integer status, Integer userId, String orderId) {
        List<Order> orders = orderMapper.lookPay(status, userId, orderId);
        return new Result().success(orders);
    }

    @Override
    public Result pay(UpOrder upOrder) throws NoSuchFieldException {

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);


        Long payTimeout = redisTemplate.getExpire("pay_code");
        if(payTimeout != null){
            return new Result().success(200,"请勿重复提交订单");
        }

        List<String> noPayList = OnlyOneClassConfig.gson.fromJson(upOrder.getNoPayList(), List.class);
        if(noPayList == null) {
            return new Result().success(200,"订单错误，请尝试再次提交");
        }

        redisTemplate.opsForValue().set("pay_code", 1);
        redisTemplate.expire("pay_code", 1, TimeUnit.SECONDS);
        for (String noPayCode : noPayList) {

            String noPayOrderJson = (String) redisTemplate.opsForValue().get(noPayCode);
            if (noPayOrderJson != null) {
                byte[] bt = noPayOrderJson.getBytes();
                MessageDigest sha = null;
                try {
                    sha = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    throw new NoSuchFieldException();
                }
                sha.update(bt);
                String digest = CodeConversionUtil.byteToHex(sha.digest());
                if (!noPayCode.contains(digest)) {
                    //记录恶意行为
                    return new Result().success(200, "订单校验失败,可能发生篡改,恶意行为已记录");
                }

                //json转复杂类型map代码，直接转换会破坏原有的数据结构
                Type type = new TypeToken<Map<Integer, Map<Order, List<OrderItem>>>>() {
                }.getType();
                Map<Integer, Map<Order, List<OrderItem>>> noPayOrderMap = OnlyOneClassConfig.gson.fromJson(noPayOrderJson, type);


                //记录库存不足的商品名称
                List<Product> products = new LinkedList<>();
                List<String> noProductList = new ArrayList<>();
                for (Map.Entry<Integer, Map<Order, List<OrderItem>>> integerMapEntry : noPayOrderMap.entrySet()) {

                    Map<Order, List<OrderItem>> orderListMap = integerMapEntry.getValue();
                    for (Map.Entry<Order, List<OrderItem>> orderListEntry : orderListMap.entrySet()) {
                        Order order = orderListEntry.getKey();
                        List<OrderItem> orderItemList = orderListEntry.getValue();
                        for (int i = 0; i < orderItemList.size(); i++) {
                            Product product = productMapper.findProductInfoById(orderItemList.get(i).getProductId());
                            products.add(product);
                            if (orderItemList.get(i).getProductCount() > product.getProductStock()) {
                                noProductList.add(orderItemList.get(i).getProductName());
                            }
                        }
                    }
                }
                if (!noProductList.isEmpty()) {
                    return new Result().success(noProductList);
                }

                for (Map.Entry<Integer, Map<Order, List<OrderItem>>> integerMapEntry : noPayOrderMap.entrySet()) {

                    Map<Order, List<OrderItem>> orderListMap = integerMapEntry.getValue();
                    for (Map.Entry<Order, List<OrderItem>> orderListEntry : orderListMap.entrySet()) {
                        Order order = orderListEntry.getKey();
                        List<OrderItem> orderItemList = orderListEntry.getValue();
                        for (int i = 0; i < orderItemList.size(); i++) {
                            Product product = products.get(i);
                            //卖出，更新库存，更新月销量，更新月销售额
                            product.setProductStock(product.getProductStock() - orderItemList.get(i).getProductCount());
                            productMapper.updateProduct(product);
                            goodsMapper.saleGoods(orderItemList.get(i).getProductCount()
                                    , product.getGoodsId());
                            //店铺更新月销售额

                            orderItemMapper.createOrderItem(orderItemList.get(i));
                        }
                        order.setStatus(1);
                        order.setCreateTime(TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S));
                        order.setOrderCount(orderItemList.size());
                        order.setUserId(upOrder.getUserId());
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

    @Transactional(readOnly = true)
    @Override
    public Result list(Integer page, Integer rows, Order order) {
        long count = orderMapper.count(order);
        if (count != 0) {

            Integer start = (page - 1) * rows;

            PageHelper.startPage(start, rows);
            List<Order> orderList = orderMapper.list(order);
            if (orderList != null && orderList.size() > 0) {
                PageInfo pageInfo = new PageInfo(orderList);
                pageInfo.setPageNum(page);
                pageInfo.setTotal(count);

                return new Result().success(pageInfo);
            }
        }

        return new Result().success(200, "无数据");
    }

    @Override
    public Result updateStatus(Integer status, String[] orderIds, Integer userId, Integer shopId) {
        for (String orderId : orderIds) {
            if (status == 4) {
                orderMapper.updateOrderStatus(TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S), status, orderId, userId, shopId);
            } else {
                orderMapper.updateOrderStatus(null, status, orderId, userId, shopId);
            }
        }
        return new Result().success();
    }

    @Transactional(readOnly = true)
    @Override
    public Result findOrderItemByOrderId(Integer id) {
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(id);
        return new Result().success(orderItemList);
    }
}
