package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.Result;
import com.cys.search.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result deleteCartItem(Integer productId,HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.deleteCartItem(productId,token);
    }
    @DeleteMapping("/clear")
    public Result clearCart(HttpServletRequest request){
        String token = ssoService.getTokenByCookie(request);
        if(token == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.clearCart(token);
    }
}
