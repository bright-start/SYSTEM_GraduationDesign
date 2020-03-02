package com.cys.system.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shop {
    private Integer id;
    private String shopName;
    private String shopDesc;
    private String shopImage;
    private String shopCompany;
    private String shopLegelPerson;
    private String shopBankCard;
    private Integer shopLove;
    private Integer shopViews;
    private String shopHotGoods;
    private Integer shopAllViews;
    private Double shopTradingVolume;
    private Double shopAllTradingVolume;
    private String shopCreateTime;
    private Integer shopStatus;

    private List<Goods> goodsList = new LinkedList<>();
    private User user = new User();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopCompany() {
        return shopCompany;
    }

    public void setShopCompany(String shopCompany) {
        this.shopCompany = shopCompany;
    }

    public String getShopLegelPerson() {
        return shopLegelPerson;
    }

    public void setShopLegelPerson(String shopLegelPerson) {
        this.shopLegelPerson = shopLegelPerson;
    }

    public String getShopBankCard() {
        return shopBankCard;
    }

    public void setShopBankCard(String shopBankCard) {
        this.shopBankCard = shopBankCard;
    }

    public Integer getShopLove() {
        return shopLove;
    }

    public void setShopLove(Integer shopLove) {
        this.shopLove = shopLove;
    }

    public Integer getShopViews() {
        return shopViews;
    }

    public void setShopViews(Integer shopViews) {
        this.shopViews = shopViews;
    }

    public String getShopHotGoods() {
        return shopHotGoods;
    }

    public void setShopHotGoods(String shopHotGoods) {
        this.shopHotGoods = shopHotGoods;
    }

    public Integer getShopAllViews() {
        return shopAllViews;
    }

    public void setShopAllViews(Integer shopAllViews) {
        this.shopAllViews = shopAllViews;
    }

    public Double getShopTradingVolume() {
        return shopTradingVolume;
    }

    public void setShopTradingVolume(Double shopTradingVolume) {
        this.shopTradingVolume = shopTradingVolume;
    }

    public Double getShopAllTradingVolume() {
        return shopAllTradingVolume;
    }

    public void setShopAllTradingVolume(Double shopAllTradingVolume) {
        this.shopAllTradingVolume = shopAllTradingVolume;
    }

    public String getShopCreateTime() {
        return shopCreateTime;
    }

    public void setShopCreateTime(String shopCreateTime) {
        this.shopCreateTime = shopCreateTime;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }
}
