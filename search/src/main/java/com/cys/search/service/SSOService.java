package com.cys.search.service;

import com.cys.search.pojo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface SSOService {
    Map<String, Object> getUser(HttpServletRequest request);

    String getRoleByCookie(HttpServletRequest request);

    Integer getUserIdByCookie(HttpServletRequest request);

    String getTokenByCookie(HttpServletRequest request);

    List<String> getNoPayCode(HttpServletRequest request);

    Result getTTL(String payCode);
}