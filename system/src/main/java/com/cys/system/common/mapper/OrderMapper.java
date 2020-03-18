package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    List<Order> lookPay(@Param("userId")Integer userId,@Param("orderId")String orderId);

    void createOrder(Order order);

    void updateOrderStatus(@Param("overTime")String overTime,@Param("status")Integer status,@Param("orderId")String orderId,@Param("userId")Integer userId);
}
