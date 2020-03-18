package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Cart;

public interface CartMapper {
    Cart listCart(Integer userId);

    void saveCart(Cart cart);

    void clearCart(Integer userId);

    void updateCart();
}
