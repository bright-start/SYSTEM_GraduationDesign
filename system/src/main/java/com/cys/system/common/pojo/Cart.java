package com.cys.system.common.pojo;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Cart {
    private Integer id;
    private Integer cartCount;
    private Double totalPrice;
    private Double postage;
    private Double discount;
    private Integer userId;
    private String createTime;

    private List<CartItem> cartItemList = new LinkedList<>();

    private Map<Integer,List<CartItem>> cartItemMap = new LinkedHashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public void setCartItemMap(Map<Integer, List<CartItem>> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Map<Integer, List<CartItem>> getCartItemMap() {
        return cartItemMap;
    }
}
