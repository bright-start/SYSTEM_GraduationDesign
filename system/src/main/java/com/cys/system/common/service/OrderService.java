package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.UpOrder;
import com.cys.system.common.pojo.Order;

import java.util.List;

public interface OrderService {
    Result buildOrder(Integer[] ids) throws NoSuchFieldException;

    Result lookNoPay(List<String> noPayList);

    Result lookPay(Integer status,Integer userId,String orderId);

    Result pay(UpOrder upOrder) throws NoSuchFieldException;

    Result list(Integer page, Integer rows, Order order);

    Result updateStatus(Integer status, String[] ids,Integer userId,Integer shopId);

    Result findOrderItemByOrderId(Integer id);

    Result deleteOrder(String payToken);
}
