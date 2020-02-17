package com.cys.system.common.filter;

import com.cys.system.common.callcontroller.TokenController;
import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.User;
import com.cys.system.common.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
public class TokenFilter implements HandlerInterceptor {

    @Autowired
    private TokenController tokenController;

    @Autowired
    private SSOService ssoService;

    protected String loginUrl = "http://www.cys.com:9200/sso/sso/html/login.html";

    protected final static String COOKIENAME="SYS-TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        Cookie[] cookies = request.getCookies();
//
//        if(!(cookies != null && cookies.length > 0)){
//            response.sendRedirect(loginUrl);
//            return false;
//        }
//        String token = null;
//        for (Cookie cookie : cookies) {
//            if(COOKIENAME.equals(cookie.getName())){
//                token = cookie.getValue();
//                break;
//            }
//        }
//        if(token == null){
//            response.sendRedirect(loginUrl);
//            return false;
//        }

//        Result result = tokenController.getToken(token);
//        if(result.getData() == null){
//            response.sendRedirect(loginUrl);
//            return false;
//        }
//        return true;

        Map<String,Object> userMap = ssoService.getUser(request);
        if(userMap == null || userMap.isEmpty()){
            response.sendRedirect(loginUrl);
            return false;
        }
        return true;
    }
}
