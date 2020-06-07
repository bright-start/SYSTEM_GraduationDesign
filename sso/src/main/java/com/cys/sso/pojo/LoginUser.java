package com.cys.sso.pojo;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginUser extends UsernamePasswordAuthenticationToken {
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
