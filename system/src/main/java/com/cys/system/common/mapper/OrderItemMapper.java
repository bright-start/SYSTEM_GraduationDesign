package com.cys.system.common.mapper;

import com.cys.system.common.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    void createOrderItem(OrderItem orderItem);

    List<OrderItem> findOrderItemByOrderId(Integer id);
}
