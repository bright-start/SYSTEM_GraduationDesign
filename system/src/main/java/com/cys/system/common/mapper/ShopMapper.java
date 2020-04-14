package com.cys.system.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ShopMapper {
    @Select("select count(shop_id) from shop where shop_status=0")
    long countApplyShop();

    @Select("SELECT\n" +
            "\"public\".\"_user\".username,\n" +
            "\"public\".shop.\"shop_id\",\n" +
            "\"public\".shop.\"shop_name\",\n" +
            "\"public\".shop.\"shop_image\",\n" +
            "\"public\".shop.\"shop_desc\",\n" +
            "\"public\".shop.\"shop_company\",\n" +
            "\"public\".shop.\"shop_bank_card\",\n" +
            "\"public\".shop.\"shop_legel_person\",\n" +
            "\"public\".shop.\"shop_status\",\n" +
            "\"public\".shop.\"shop_send_phone\",\n" +
            "\"public\".shop.\"shop_address\",\n" +
            "\"public\".\"_user\".\"bind_phone\",\n" +
            "\"public\".shop.\"shop_createtime\"\n" +
            "FROM\n" +
            "\"public\".shop\n" +
            "JOIN \"public\".\"_user\"\n" +
            "ON \"public\".shop.\"shop_id\" = \"public\".\"_user\".\"shop_id\"\n" +
            "WHERE\n" +
            "\"public\".shop.\"shop_status\"=0\n" +
            "ORDER BY\n" +
            "\"public\".shop.\"shop_createtime\" DESC")
    List<Map> listApplyShop();

    @Update("update shop set shop_status=#{status} where shop_id=#{shopId}")
    void examine(@Param("shopId") Integer id, @Param("status") Integer status);

    @Select("SELECT\n" +
            "\"public\".shop.\"shop_name\",\n" +
            "\"public\".\"_user\".\"bind_phone\"\n" +
            "FROM\n" +
            "\"public\".shop\n" +
            "JOIN \"public\".\"_user\"\n" +
            "ON \"public\".shop.\"shop_id\" = \"public\".\"_user\".\"shop_id\"\n" +
            "WHERE\n" +
            "\"public\".shop.\"shop_id\"=#{shopId}")
    Map getShopInfoByShopId(Integer shopId);
}
