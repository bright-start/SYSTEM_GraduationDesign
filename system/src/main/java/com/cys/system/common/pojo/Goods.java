package com.cys.system.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods {
    private Integer id;
    private String goodsName;
    private String goodsCaption;
    private String goodsImages;
    private Double goodsPrice;
    private String goodsIntroduce;
    private String goodsPackageList;
    private String goodsSaleService;
    private String goodsDiscount;
    private String goodsPostage;
    private Integer monthSale;
    private Integer recomment;
    private Integer status;
    private Integer isEnableSpec;
    private String createTime;
    private String upperSelfTime;
    private String lowerSelfTime;
    private Integer brandId;
    private Integer shopId;
    private Integer areaId;

    private Brand brand = new Brand();
    private Shop shop = new Shop();
    private Area area = new Area();

    private List<Product> productList = new LinkedList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCaption() {
        return goodsCaption;
    }

    public void setGoodsCaption(String goodsCaption) {
        this.goodsCaption = goodsCaption;
    }

    public String getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(String goodsImages) {
        this.goodsImages = goodsImages;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public String getGoodsPackageList() {
        return goodsPackageList;
    }

    public void setGoodsPackageList(String goodsPackageList) {
        this.goodsPackageList = goodsPackageList;
    }

    public String getGoodsSaleService() {
        return goodsSaleService;
    }

    public void setGoodsSaleService(String goodsSaleService) {
        this.goodsSaleService = goodsSaleService;
    }

    public String getGoodsDiscount() {
        return goodsDiscount;
    }

    public void setGoodsDiscount(String goodsDiscount) {
        this.goodsDiscount = goodsDiscount;
    }

    public String getGoodsPostage() {
        return goodsPostage;
    }

    public void setGoodsPostage(String goodsPostage) {
        this.goodsPostage = goodsPostage;
    }

    public Integer getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(Integer monthSale) {
        this.monthSale = monthSale;
    }

    public Integer getRecomment() {
        return recomment;
    }

    public void setRecomment(Integer recomment) {
        this.recomment = recomment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsEnableSpec() {
        return isEnableSpec;
    }

    public void setIsEnableSpec(Integer isEnableSpec) {
        this.isEnableSpec = isEnableSpec;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpperSelfTime() {
        return upperSelfTime;
    }

    public void setUpperSelfTime(String upperSelfTime) {
        this.upperSelfTime = upperSelfTime;
    }

    public String getLowerSelfTime() {
        return lowerSelfTime;
    }

    public void setLowerSelfTime(String lowerSelfTime) {
        this.lowerSelfTime = lowerSelfTime;
    }


    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
