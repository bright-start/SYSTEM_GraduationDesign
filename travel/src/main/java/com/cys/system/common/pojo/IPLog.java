package com.cys.system.common.pojo;

public class IPLog {
    private Integer ipLogId;
    private String ipAddr;
    private String browseTime;
    private String browseDateDay;
    private String browseDateMonth;
    private String browseDateYear;

    public Integer getIpLogId() {
        return ipLogId;
    }

    public void setIpLogId(Integer ipLogId) {
        this.ipLogId = ipLogId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getBrowseDateDay() {
        return browseDateDay;
    }

    public void setBrowseDateDay(String browseDateDay) {
        this.browseDateDay = browseDateDay;
    }

    public String getBrowseDateMonth() {
        return browseDateMonth;
    }

    public void setBrowseDateMonth(String browseDateMonth) {
        this.browseDateMonth = browseDateMonth;
    }

    public String getBrowseDateYear() {
        return browseDateYear;
    }

    public void setBrowseDateYear(String browseDateYear) {
        this.browseDateYear = browseDateYear;
    }

    public String getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(String browseTime) {
        this.browseTime = browseTime;
    }
}
