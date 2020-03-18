package com.cys.system.common.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "goods_summary_doc", type = "goods_summary")
public class GoodsSummary implements Serializable {
    @Id
    private Integer productId;
    @Field(analyzer = "ik_smart", type = FieldType.Text)
    private String goodsName;
    private String goodsImage;
    private Integer goodsId;
    private Double goodsPrice;
    private String areaName;
    private Integer goodsRecomment;
    private String shopName;
    private String goodsMonthSale;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getGoodsRecomment() {
        return goodsRecomment;
    }

    public void setGoodsRecomment(Integer goodsRecomment) {
        this.goodsRecomment = goodsRecomment;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsMonthSale() {
        return goodsMonthSale;
    }

    public void setGoodsMonthSale(String goodsMonthSale) {
        this.goodsMonthSale = goodsMonthSale;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }
}

