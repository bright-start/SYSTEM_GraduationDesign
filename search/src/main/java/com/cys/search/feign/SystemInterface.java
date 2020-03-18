package com.cys.search.feign;


import com.cys.search.feign.failback.FailBack;
import com.cys.search.pojo.AuthUrl;
import com.cys.search.pojo.UpOrder;
import com.cys.search.pojo.SearchEntity;
import com.cys.search.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "system.cys.com",path = "/system",fallback = FailBack.class)
public interface SystemInterface {
    @GetMapping("/area/findAreaList")
    Result findAreaList();

    @GetMapping("/goods/findGoodGoods")
    Result findGoodGoods();

    @PostMapping(value = "/goods_engine/search",consumes="application/json")
    Result search(@RequestBody SearchEntity goods);

    @GetMapping("/goods/findOne")
    Result getGoodsById(@RequestParam Integer id);

    @GetMapping("/goods/recomment")
    Result recomment(@RequestParam Integer id,@RequestParam Integer status);

    @GetMapping("/cart/add")
    Result addCart(@RequestParam Integer productId, @RequestParam Integer num, @RequestParam String token);

    @GetMapping("/cart/look")
    Result lookCart(@RequestParam String token);

    @DeleteMapping("/cart/delete")
    Result deleteCartItem(@RequestParam Integer productId,@RequestParam String token);

    @DeleteMapping("/cart/clear")
    Result clearCart(@RequestParam String token);

    @PostMapping(value = "/order/build",consumes = "application/json")
    Result buildOrder(@RequestBody Integer[] ids);

    @GetMapping("/order/lookNoPay")
    Result lookNoPay(@RequestParam String noPayList);

    @GetMapping(value = "/order/lookPay")
    Result lookPay(@RequestParam Integer userId,@RequestParam(required = false) String orderId);

    @PostMapping(value = "/order/pay",consumes = "application/json")
    Result pay(@RequestBody UpOrder upOrder);

    @GetMapping(value = "/auth/getAll",consumes = "application/json")
    List<AuthUrl> getAllAuthUrl();
}
