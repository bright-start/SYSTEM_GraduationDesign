package com.cys.system.common.common.pojo;

public class UpOrder {
    private String creator;
    private Integer phone;
    private String address;
    private String payType;
    private String noPayList;

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
}
