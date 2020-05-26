package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.Result;
import com.cys.search.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController{

    @Autowired
    private SystemInterface systemInterface;

    @Autowired
    private SSOService ssoService;

    @GetMapping("/add")
    public Result addCart(Integer productId, Integer num, HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.addCart(productId,num,token);
    }

    @GetMapping("/look")
    public Result lookCart(HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.lookCart(token);
    }

    @DeleteMapping("/delete")
    public Result deleteCartItem(Integer cartId,Integer[] ids,HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.deleteCartItem(cartId,ids,token);
    }
    @DeleteMapping("/clear")
    public Result clearCart(HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.clearCart(token);
    }
    @GetMapping("/addNum")
    public Result addNum(@RequestParam Integer cartId, @RequestParam Integer cartItemId, @RequestParam Integer num, HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.addNum(cartId,cartItemId,num,token);
    }
}
