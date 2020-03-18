package com.cys.system.common.pojo;

public class Msg {
    private Integer id;
    private String message;
    private Integer msgType;
    private Integer msgBelong;
    private String createTime;
    private Integer read;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getMsgBelong() {
        return msgBelong;
    }

    public void setMsgBelong(Integer msgBelong) {
        this.msgBelong = msgBelong;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }
}
