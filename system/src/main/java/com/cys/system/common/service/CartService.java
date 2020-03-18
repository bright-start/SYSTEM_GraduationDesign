package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;


public interface CartService {
    Result addCart(Integer productId, Integer productNum, Integer userId);

    Result lookCart(Integer userId);

    Result deleteCartItem(Integer productId);

    Result clearCart(Integer userId);
}
