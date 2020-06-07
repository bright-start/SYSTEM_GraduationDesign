package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;


public interface CartService {
    Result addCart(Integer productId, Integer productNum, Integer userId);

    Result lookCart(Integer userId);

    Result deleteCartItem(Integer cartId,Integer[] ids,Integer userId) throws UnauthorizedException;

    Result clearCart(Integer userId);

    Result addNum(Integer cartId,Integer cartItemId,Integer num,Integer userId) throws UnauthorizedException;
}
