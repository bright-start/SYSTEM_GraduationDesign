package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.service.CartService;
import com.cys.system.common.service.SSOService;
import com.cys.system.common.service.impl.SSOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Resource
    private SSOService ssoService;

    private Integer volidAndGetUserId(String token) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(token);
        if(userMap == null || userMap.isEmpty()){
            throw new UnauthorizedException("请登陆后重试");
        }
        Integer userId = (Integer) userMap.get("userId");
        if (userId == null) {
            throw new UnauthorizedException("身份认证失败");
        }
        return userId;
    }

    @GetMapping("/add")
    public Result addCart(Integer productId, Integer num,String token){
        try {
            Integer userId = volidAndGetUserId(token);
            return cartService.addCart(productId,num,userId);
        } catch (UnauthorizedException e) {
            return new Result().success(e.getMessage());
        }
    }

    @GetMapping("/look")
    public Result lookCart(String token){
        try {
            Integer userId = volidAndGetUserId(token);
            return cartService.lookCart(userId);
        } catch (UnauthorizedException e) {
            return new Result().success(e.getMessage());
        }
    }

    @GetMapping("/addNum")
    public Result addNum(Integer cartId,Integer cartItemId,Integer num,String token){
        try {
            Integer userId = volidAndGetUserId(token);
            return cartService.addNum(cartId,cartItemId,num,userId);
        } catch (UnauthorizedException e) {
            return new Result().success(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Result deleteCartItem(Integer cartId,Integer[] ids,String token){
        try {
            Integer userId = volidAndGetUserId(token);
            return cartService.deleteCartItem(cartId,ids,userId);
        } catch (UnauthorizedException e) {
            return new Result().success(e.getMessage());
        }
    }
    @DeleteMapping("/clear")
    public Result clearCart(String token){
        try {
            Integer userId = volidAndGetUserId(token);
            return cartService.clearCart(userId);
        } catch (UnauthorizedException e) {
            return new Result().success(e.getMessage());
        }
    }
}
