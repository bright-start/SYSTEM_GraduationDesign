package com.cys.search.feign;


import com.cys.search.feign.failback.FailBack;
import com.cys.search.pojo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(value = "system.cys.com",path = "/system",fallback = FailBack.class)
public interface SystemInterface {
    @GetMapping("/area/findAreaList")
    Result findAreaList();

    @GetMapping("/bulletin/loadBulletinList")
    Result loadBulletinList();

    @GetMapping("/article/loadArticleList")
    Result loadArticleList();

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

    @GetMapping("/cart/addNum")
    Result addNum(@RequestParam Integer cartId,@RequestParam Integer cartItemId, @RequestParam Integer num, @RequestParam String token);

    @DeleteMapping("/cart/delete")
    Result deleteCartItem(@RequestParam Integer cartId,@RequestParam Integer[] ids,@RequestParam String token);

    @DeleteMapping("/cart/clear")
    Result clearCart(@RequestParam String token);

    @PostMapping(value = "/order/build",consumes = "application/json")
    Result buildOrder(@RequestBody Integer[] ids);

    @GetMapping("/order/lookNoPay")
    Result lookNoPay(@RequestParam String noPayList);

    @GetMapping(value = "/order/lookPay")
    Result lookPay(@RequestParam(required = false) Integer status,@RequestParam Integer userId,@RequestParam(required = false) String orderId);

    @PostMapping(value = "/order/pay",consumes = "application/json")
    Result pay(@RequestBody UpOrder upOrder);

    @GetMapping(value = "/auth/getAll",consumes = "application/json")
    Map<String, List<AuthUrl>> listAllRoleAuthUrl();

    @DeleteMapping("/order/delete")
    Result deleteOrder(@RequestParam String orderToken);

    @GetMapping("/article/get")
    Result getArticleById(@RequestParam Integer id);

    @GetMapping("/article/increaseBrowseNum")
    Result increaseBrowseNum(@RequestParam Integer userId,@RequestParam Integer articleId);

    @PutMapping("/article/increaseLoveNum")
    Result increaseLoveNum(@RequestParam Integer userId,@RequestParam Integer articleId, @RequestParam Integer islove);

    @PostMapping(value = "/article/command/commit",consumes = "application/json")
    Result commitCommand(@RequestBody Command command);

    @DeleteMapping("/article/command/delete")
    Result deleteCommand(@RequestParam Integer userId, @RequestParam Integer commandId);
}
