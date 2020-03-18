package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.UpOrder;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Order;
import com.cys.system.common.pojo.OrderItem;
import com.cys.system.common.service.OrderService;
import com.cys.system.common.service.SSOService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SSOService ssoService;

    private static final Gson gson = new Gson();

    @PostMapping("/build")
    public Result buildOrder(@RequestBody Integer[] ids) {
        return orderService.buildOrder(ids);
    }

    @GetMapping("/lookNoPay")
    public Result lookNoPay(String noPayList) {
        List<String> noPays = gson.fromJson(noPayList, List.class);

        return orderService.lookNoPay(noPays);
    }

    @GetMapping("/lookPay")
    public Result lookPay(Integer userId,String orderId) {
        return orderService.lookPay(userId,orderId);
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody UpOrder upOrder) {
        return orderService.pay(upOrder);
    }
}
