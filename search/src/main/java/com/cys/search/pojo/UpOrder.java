package com.cys.search.pojo;

import java.util.LinkedList;
import java.util.List;

public class UpOrder {
    private String creator;
    private String phone;
    private String address;
    private String payType;
    private String noPayList;
    private Integer userId;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNoPayList() {
        return noPayList;
    }

    public void setNoPayList(String noPayList) {
        this.noPayList = noPayList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
