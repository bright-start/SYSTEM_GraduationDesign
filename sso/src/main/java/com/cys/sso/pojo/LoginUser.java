package com.cys.sso.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {
    private Integer userId;

    private Integer shopId;

    public LoginUser(Integer userId,String username, String password,Collection<? extends GrantedAuthority> authorities,Integer shopId) {
        super(username, password, authorities);
        this.userId = userId;
        this.shopId = shopId;
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
}
