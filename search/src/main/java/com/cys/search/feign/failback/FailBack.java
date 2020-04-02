package com.cys.search.feign.failback;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class FailBack implements SystemInterface {
    @Override
    public Result findAreaList() {
        return new Result().success(503,"无数据");
    }

    @Override
    public Result loadBulletinList() {
        return new Result().success(503,"无数据");
    }

    @Override
    public Result loadArticleList() {
        return new Result().success(503,"无数据");
    }

    @Override
    public Result findGoodGoods() {
        return new Result().success(503,"无数据");
    }

    @Override
    public Result search(SearchEntity goods) {
        return new Result().success(503,"无数据");
    }

    @Override
    public Result getGoodsById(Integer id) {
        return new Result().success(503,"无数据");
    }

    @Override
    public Result recomment(Integer id,Integer status) {
        return new Result().success(503,"error");
    }

    @Override
    public Result addCart(Integer productId, Integer num, String token) {
        return new Result().success(503,"error");
    }

    @Override
    public Result lookCart(String token) {
        return new Result().success(503,"error");
    }

    @Override
    public Result deleteCartItem(Integer productId,String token) {
        return new Result().success(503,"error");
    }

    @Override
    public Result clearCart(String token) {
        return new Result().success(503,"error");
    }

    @Override
    public Result buildOrder(Integer[] ids) {
        return new Result().success(503,"error");
    }

    @Override
    public Result lookNoPay(String noPayList) {
        return new Result().success(503,"error");
    }

    @Override
    public Result lookPay(Integer status,Integer userId,String orderId) {
        return new Result().success(503,"error");
    }

    @Override
    public Result pay(UpOrder upOrder) {
        return new Result().success(503,"error");
    }

    @Override
    public Result deleteOrder(String orderToken) {
        return new Result().success(503,"error");
    }

    @Override
    public Map<String, List<AuthUrl>> listAllRoleAuthUrl() {
        return new HashMap<>();
    }

    @Override
    public Result getArticleById(Integer id) {
        return new Result().success(503,"error");
    }

    @Override
    public Result increaseBrowseNum(Integer userId,Integer articleId) {
        return new Result().success(503,"error");
    }

    @Override
    public Result increaseLoveNum(Integer userId,Integer articleId, Integer islove) {
        return new Result().success(503,"error");
    }

    @Override
    public Result commitCommand(Command command) {
        return new Result().success(503,"error");
    }

    @Override
    public Result deleteCommand(Integer userId,Integer commandId) {
        return new Result().success(503,"error");
    }
}
