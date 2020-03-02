package com.cys.system.common.pojo;

public class AuthUrl {
    private Integer permission_Id;
    private String permission_url;

    public Integer getPermission_Id() {
        return permission_Id;
    }

    public void setPermission_Id(Integer permission_Id) {
        this.permission_Id = permission_Id;
    }

    public String getPermission_url() {
        return permission_url;
    }

    public void setPermission_url(String permission_url) {
        this.permission_url = permission_url;
    }

    @Override
    public String toString() {
        return  permission_url;
    }
}
