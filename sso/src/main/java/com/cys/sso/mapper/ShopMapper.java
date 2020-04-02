package com.cys.sso.mapper;

import com.cys.sso.pojo.Shop;

public interface ShopMapper {
    void saveShopInfo(Shop shop);

    Shop existShop(String shopName);
}
