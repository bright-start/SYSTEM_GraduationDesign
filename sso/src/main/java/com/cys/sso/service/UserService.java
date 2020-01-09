package com.cys.sso.service;

import com.cys.sso.pojo.Result;
import com.cys.sso.pojo.User;
import com.cys.sso.pojo.UserFingerprint;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    Result registry(UserFingerprint userFingerprint,HttpServletRequest request);

    User findPasswordByUsername(String username);

    void logout(HttpServletRequest request);

    Result checkUsername(String username);
}
