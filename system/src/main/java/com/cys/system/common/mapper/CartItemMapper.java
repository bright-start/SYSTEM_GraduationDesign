package com.cys.system.common.mapper;

import com.cys.system.common.pojo.CartItem;

public interface CartItemMapper {
    void updateCartItem(CartItem cartItem);

    void saveCartItem(CartItem cartItem);

    void deleteCartItem(Integer productId);

    Long findCartItemByCartId(Integer cartId);

    CartItem getCartItemByItemId(Integer id);
}
