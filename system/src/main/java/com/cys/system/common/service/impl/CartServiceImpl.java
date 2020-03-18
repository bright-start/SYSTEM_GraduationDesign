package com.cys.system.common.service.impl;

import com.cys.system.common.pojo.Cart;
import com.cys.system.common.pojo.CartItem;
import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.CartItemMapper;
import com.cys.system.common.mapper.CartMapper;
import com.cys.system.common.mapper.ProductMapper;
import com.cys.system.common.pojo.Product;
import com.cys.system.common.service.CartService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = {Exception.class})
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CartItemMapper cartItemMapper;

    @Override
    public Result addCart(Integer productId, Integer productNum, Integer userId) {


        Cart cart = cartMapper.listCart(userId);

        if(cart != null) {
            List<CartItem> cartItemList = cart.getCartItemList();
            Map<Integer, CartItem> map = new LinkedHashMap<>();
            for (CartItem cartItem : cartItemList) {
                map.put(cartItem.getProductId(), cartItem);
            }

            boolean containsKey = map.containsKey(productId);
            if (containsKey) {
                CartItem cartItem = map.get(productId);
                cartItem.setProductCount(cartItem.getProductCount() + productNum);

                if(cartItem.getProductCount() == 0){
                    cartItemMapper.deleteCartItem(productId);
                }else {
                    cartItemMapper.updateCartItem(cartItem);
                }
                Long cartItemCount = cartItemMapper.findCartItemByCartId(cart.getId());
                if(cartItemCount == 0){
                    cart.setCartCount(0);
                    cart.setDiscount(0.00);
                    cart.setPostage(0.00);
                    cart.setTotalPrice(0.00);
                    cart.setCartItemList(null);
                    cartMapper.updateCart();
                }

            } else {
                CartItem newCartItem = createCartItem(cart,productId);

                cartItemMapper.saveCartItem(newCartItem);
            }
        }else {
            Cart newCart = new Cart();

            newCart.setUserId(userId);
            newCart.setCreateTime(TimeConverter.DateToString(new Date()));

            cartMapper.saveCart(newCart);

            CartItem newCartItem = createCartItem(newCart,productId);
            cartItemMapper.saveCartItem(newCartItem);
        }

        return new Result().success();
    }

    private CartItem createCartItem(Cart cart,Integer productId){
        CartItem newCartItem = new CartItem();
        newCartItem.setCartId(cart.getId());

        Product product = productMapper.findProductInfoById(productId);

        newCartItem.setProductId(product.getId());
        newCartItem.setProductImage(product.getProductImage());
        newCartItem.setProductName(product.getProductName());
        newCartItem.setProductPrice(product.getProductPrice());
        newCartItem.setProductCount(1);
        newCartItem.setPostage(product.getPostage());
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setShopId(product.getShopId());
        newCartItem.setShopName(product.getShopName());
        newCartItem.setAreaName(product.getAreaName());
        newCartItem.setSpec(product.getSpec());

        return newCartItem;
    }

    @Transactional(readOnly = true)
    @Override
    public Result lookCart(Integer userId) {
        Cart cart = cartMapper.listCart(userId);

        List<CartItem> cartItemList = cart.getCartItemList();

        Map<Integer,List<CartItem>> cartItemMap = new LinkedHashMap<>();
        for (CartItem cartItem : cartItemList) {
            List<CartItem> cartItems = new LinkedList<>();
            cartItemMap.put(cartItem.getShopId(),cartItems);
        }

        for (Integer key : cartItemMap.keySet()) {
            for (CartItem cartItem : cartItemList) {
                if(cartItem.getShopId() == key){
                    List<CartItem> cartItems = cartItemMap.get(key);
                    cartItems.add(cartItem);
                    cartItemMap.put(key,cartItems);
                }
            }
        }

        cart.setCartItemMap(cartItemMap);
        cart.setCartItemList(null);
        return new Result().success(cart);
    }

    @Override
    public Result deleteCartItem(Integer productId) {
        cartItemMapper.deleteCartItem(productId);
        return new Result().success();
    }

    @Override
    public Result clearCart(Integer userId) {
        cartMapper.clearCart(userId);
        return new Result().success();
    }


}
