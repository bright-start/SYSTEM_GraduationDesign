package com.cys.search.feign.failback;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.AuthUrl;
import com.cys.search.pojo.UpOrder;
import com.cys.search.pojo.SearchEntity;
import com.cys.search.pojo.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FailBack implements SystemInterface {
    @Override
    public Result findAreaList() {
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
    public Result lookPay() {
        return new Result().success(503,"error");
    }

    @Override
    public Result pay(UpOrder upOrder) {
        return new Result().success(503,"error");
    }

    @Override
    public List<AuthUrl> getAllAuthUrl() {
        return null;
    }
}
