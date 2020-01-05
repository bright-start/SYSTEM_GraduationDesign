package com.cys.system.common.pojo;

import com.cys.sso.pojo.User;
import com.cys.sso.pojo.UserInfo;

public class UserFingerprint {
    private User user;
    private UserInfo userInfo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
