package com.cys.search.filter;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.AuthUrl;
import com.cys.search.service.SSOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
public class TokenFilter implements HandlerInterceptor {

    @Autowired
    private SSOService ssoService;

//    @Autowired
//    private SystemInterface systemInterface;

    protected String loginUrl = "http://www.cys.com:9200/sso/sso/html/login.html";

    protected final static String COOKIENAME="SYS-TOKEN";


    private Logger logger = LoggerFactory.getLogger(TokenFilter.class);

//    @PostConstruct
//    public void getAllAuthUrl(){
//        List<AuthUrl> authUrl = systemInterface.getAllAuthUrl();
//        authUrlList.addAll(authUrl);
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        StringBuffer requestURL = request.getRequestURL();



        logger.info(requestURL.toString()+"验证");
//        Map<String,Object> userMap = ssoService.getUser(request);
//        if(userMap == null || userMap.isEmpty()){
//            response.sendRedirect(loginUrl);
//            return false;
//        }
        return true;
    }
}
