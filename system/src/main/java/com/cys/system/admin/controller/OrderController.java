package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.UpOrder;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Order;
import com.cys.system.common.service.OrderService;
import com.cys.system.common.service.SSOService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SSOService ssoService;

    @PostMapping("/build")
    public Result buildOrder(@RequestBody Integer[] ids) throws NoSuchFieldException {
        return orderService.buildOrder(ids);
    }

    @GetMapping("/lookNoPay")
    public Result lookNoPay(String noPayList) {
        List<String> noPays = OnlyOneClassConfig.gson.fromJson(noPayList, List.class);

        return orderService.lookNoPay(noPays);
    }

    @DeleteMapping("/delete")
    Result deleteOrder(@RequestParam String payToken){
        return orderService.deleteOrder(payToken);
    }

    @GetMapping("/lookPay")
    public Result lookPay(@RequestParam(required = false) Integer status,Integer userId,String orderId) {
        return orderService.lookPay(status,userId,orderId);
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody UpOrder upOrder) {
        return orderService.pay(upOrder);
    }

    @PostMapping("/list")
    public Result list(Integer page,Integer rows,@RequestBody Order order,HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if(userMap != null && !userMap.isEmpty()) {
            throw new UnauthorizedException();
        }
        Integer shopId = (Integer) userMap.get("shopId");
        if(shopId == null) {
            throw new UnauthorizedException();
        }
        order.setShopId(shopId);
        return orderService.list(page,rows,order);
    }

    @PutMapping("/updateStatusByUser")
    public Result updateStatus(@RequestParam Integer status,@RequestParam String[] ids,@RequestParam Integer userId){
        return orderService.updateStatus(status,ids,userId,null);
    }

    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam Integer status,@RequestParam String[] ids,HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if(userMap != null && !userMap.isEmpty()) {
            throw new UnauthorizedException();
        }
        Integer shopId = (Integer) userMap.get("shopId");
        if(shopId == null) {
            throw new UnauthorizedException();
        }
        return orderService.updateStatus(status, ids, null,shopId);
    }

    @GetMapping("/findOrderItemByOrderId")
    public Result findOrderItemByOrderId(@RequestParam Integer id){
        return orderService.findOrderItemByOrderId(id);
    }
}
