package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.UpOrder;
import com.cys.search.pojo.Result;
import com.cys.search.service.SSOService;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController{

    @Autowired
    private SystemInterface systemInterface;

    @Autowired
    private SSOService ssoService;

    private static final Gson gson = new Gson();

    @PostMapping("/build")
    public Result buildOrder(@RequestBody Integer[] ids, HttpServletResponse response){
        Result result = systemInterface.buildOrder(ids);
        if(result.getCode() == 200) {
            String orderCode = (String) result.getData();

            Cookie newCookie = new Cookie(orderCode, "1");
            newCookie.setMaxAge(30 * 60 * 60);
            newCookie.setPath("/");
            newCookie.setHttpOnly(true);
            response.addCookie(newCookie);

            result.setData(null);
        }
        return result;
    }

    @GetMapping("/lookNoPay")
    public Result lookNoPay(HttpServletRequest request){
        List<String> noPayList = ssoService.getNoPayCode(request);
        if(noPayList.size() > 0) {
            return systemInterface.lookNoPay(gson.toJson(noPayList));
        }else {
            return new Result().success();
        }
    }

    @GetMapping("/lookPay")
    public Result lookPay(@RequestParam(required = false) Integer status,@RequestParam(required = false) String orderId, HttpServletRequest request){
        Integer userId = ssoService.getUserIdByCookie(request);
        if(userId == null){
            return new Result().success(401,"身份获取失败，请尝试重新登陆");
        }
        return systemInterface.lookPay(status,userId,orderId);
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody UpOrder upOrder, HttpServletRequest request){
        Integer userId = ssoService.getUserIdByCookie(request);

        if(userId == null){
            return new Result().success(401,"身份获取失败，请尝试重新登陆");
        }
        upOrder.setUserId(userId);
        List<String> noPayList = ssoService.getNoPayCode(request);
        if(noPayList.size() > 0) {
            upOrder.setNoPayList(gson.toJson(noPayList));
            return systemInterface.pay(upOrder);
        }else {
            return new Result().success();
        }
    }
}
