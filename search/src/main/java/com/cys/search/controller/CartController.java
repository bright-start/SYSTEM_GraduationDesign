package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.Result;
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
public class CartController extends CookValidController{

    @Autowired
    private SystemInterface systemInterface;

    @GetMapping("/add")
    public Result addCart(Integer productId, Integer num, HttpServletRequest request){
        String cookie = getTokenByCookie(request);
        if(cookie == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.addCart(productId,num,cookie);
    }

    @GetMapping("/look")
    public Result lookCart(HttpServletRequest request){
        String cookie = getTokenByCookie(request);
        if(cookie == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.lookCart(cookie);
    }

    @DeleteMapping("/delete")
    public Result deleteCartItem(Integer productId,HttpServletRequest request){
        String cookie = getTokenByCookie(request);
        if(cookie == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.deleteCartItem(productId,cookie);
    }
    @DeleteMapping("/clear")
    public Result clearCart(HttpServletRequest request){
        String cookie = getTokenByCookie(request);
        if(cookie == null){
            return new Result().success(401,"请登录后操作");
        }
        return systemInterface.clearCart(cookie);
    }
}
