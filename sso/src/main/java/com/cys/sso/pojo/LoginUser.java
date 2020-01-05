package com.cys.sso.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {
    private String bindPhone;
    private String code;

    public LoginUser(String username, String password,String bindPhone,String code, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.bindPhone = bindPhone;
        this.code =code;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
