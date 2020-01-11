package com.cys.system.common.filter;

import com.cys.system.common.callcontroller.TokenController;
import com.cys.system.common.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class TokenFilter implements HandlerInterceptor {

    @Autowired
    private TokenController tokenController;

    protected String loginUrl = "http://127.0.0.1:9200/sso/sso/html/login.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Result result = tokenController.getToken();
        if(result.getData() == null){
            response.sendRedirect(loginUrl);
            return false;
        }
        return true;
    }
}
