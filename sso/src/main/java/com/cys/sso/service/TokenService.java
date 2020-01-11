package com.cys.sso.service;

import com.cys.sso.pojo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenService {
    Result buildToken(HttpServletResponse response);
    Result getToken(HttpServletRequest request);
}
