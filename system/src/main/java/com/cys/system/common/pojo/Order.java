package com.cys.system.common.pojo;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private String orderId;
    private Integer orderCount;
    private Double totalPrice;
    private Double postage;
    private Double discount;
    private String createTime;
    private String overTime;
    private Integer status;
    private String creator;
    private Integer phone;
    private String address;
    private Integer userId;
    private Integer shopId;
    private String shopName;
    private String payType;
    private String noPayList;


    private List<OrderItem> orderItemList = new LinkedList<>();

    public Order(String orderId, Integer shopId) {
        this.orderId = orderId;
        this.shopId = shopId;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
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

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getNoPayList() {
        return noPayList;
    }

    public void setNoPayList(String noPayList) {
        this.noPayList = noPayList;
    }

    /**
     * 重写hashCode和equal使order作为map的key，不会发生内存溢出问题
     */
    @Override
    public int hashCode() {
        if(orderId == null || shopId == null){
            return super.hashCode();
        }


        /**
         * 一种标准hashCode写法
         */
        int result = 17;
        result = 31 * result + orderId.hashCode();
        result = 31 * result + shopId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            Order order = (Order) obj;
            return orderId.equals(order.getOrderId());
        }
        return false;
    }
}
