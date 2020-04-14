package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {
    List<Order> lookPay(@Param("status") Integer status,@Param("userId")Integer userId,@Param("orderId")String orderId);

    void createOrder(Order order);

    void updateOrderStatus(@Param("overTime")String overTime,@Param("status")Integer status,@Param("orderId")String orderId,@Param("userId")Integer userId,@Param("shopId")Integer shopId);

    long count(Order order);

    List<Order> list(Order order);

    @Select("SELECT\n" +
            "count(order_id)\n" +
            "FROM\n" +
            "\"public\".\"_order\"\n" +
            "WHERE\n" +
            "\"public\".\"_order\".\"shop_id\"=#{shopId} and \"public\".\"_order\".\"order_status\" < 4")
    Long getWaitDealOrderNum(Integer shopId);
}
