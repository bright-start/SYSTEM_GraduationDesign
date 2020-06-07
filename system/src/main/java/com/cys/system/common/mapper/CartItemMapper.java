package com.cys.system.common.mapper;

import com.cys.system.common.pojo.CartItem;
import org.apache.ibatis.annotations.Param;

public interface CartItemMapper {
    void updateCartItem(CartItem cartItem);

    void saveCartItem(CartItem cartItem);

    void deleteCartItem(Integer cartItemId);

    Long findCartItemByCartId(Integer cartId);

    CartItem getCartItemByItemId(Integer id);

    void addNum(@Param("cartItemId") Integer cartItemId,@Param("num")Integer num);

    Integer findCartItemByCartItemId(Integer cartItemId);
}
