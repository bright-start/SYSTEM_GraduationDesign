package com.cys.system.common.pojo;

public class IPRecord {
    private String ipAddr;
    private String firstBrowseTime;
    private String beginBrowseTime;
    private String browseTotalCount;
    private String blackList;
    private String ipAreaCity;
    private String ipAreaProvince;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getFirstBrowseTime() {
        return firstBrowseTime;
    }

    public void setFirstBrowseTime(String firstBrowseTime) {
        this.firstBrowseTime = firstBrowseTime;
    }

    public String getBeginBrowseTime() {
        return beginBrowseTime;
    }

    public void setBeginBrowseTime(String beginBrowseTime) {
        this.beginBrowseTime = beginBrowseTime;
    }


    public String getIpAreaCity() {
        return ipAreaCity;
    }

    public void setIpAreaCity(String ipAreaCity) {
        this.ipAreaCity = ipAreaCity;
    }

    public String getIpAreaProvince() {
        return ipAreaProvince;
    }

    public void setIpAreaProvince(String ipAreaProvince) {
        this.ipAreaProvince = ipAreaProvince;
    }

    public String getBrowseTotalCount() {
        return browseTotalCount;
    }

    public void setBrowseTotalCount(String browseTotalCount) {
        this.browseTotalCount = browseTotalCount;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }
}
