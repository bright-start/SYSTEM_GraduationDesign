package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.UpOrder;
import com.cys.system.common.pojo.Order;

import java.util.List;

public interface OrderService {
    Result buildOrder(Integer[] ids);

    Result lookNoPay(List<String> noPayList);

    Result lookPay(Integer userId,String orderId);

    Result pay(UpOrder upOrder);
}
