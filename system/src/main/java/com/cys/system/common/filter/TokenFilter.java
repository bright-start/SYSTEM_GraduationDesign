package com.cys.system.common.filter;

import com.cys.system.common.callcontroller.TokenController;
import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.AuthUrlMapper;
import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.pojo.User;
import com.cys.system.common.service.AuthUrlService;
import com.cys.system.common.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
@Order(2)
public class TokenFilter implements HandlerInterceptor {

    @Autowired
    private TokenController tokenController;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private AuthUrlService authUrlService;

    protected String loginUrl = "http://www.cys.com:9200/sso/sso/html/login.html";

    protected final static String COOKIENAME = "SYS-TOKEN";

    private final static List<AuthUrl> authUrlList = new LinkedList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, List<AuthUrl>> roleAuthUrl = authUrlService.listAllRoleAuthUrl();

        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap == null || userMap.isEmpty()) {
            response.sendRedirect(loginUrl);
            return false;
        }

        //获取访问的url路径
        StringBuffer requestURL = request.getRequestURL();

        for (Map.Entry<String, List<AuthUrl>> entry : roleAuthUrl.entrySet()) {
            String roleKey = entry.getKey();
            List<AuthUrl> roleAuthUrlList = entry.getValue();

            //权限路径白名单检测确认，通过白名单则放行
            if (!roleAuthUrlList.isEmpty() && roleAuthUrlList.toString().contains(requestURL)) {

                List list = (List) userMap.get("authorities");
                if (list != null && !list.isEmpty()) {
                    Map map = (Map) list.get(0);
                    if (map != null && !map.isEmpty()) {
                        String role = (String) map.get("role");
                        if (!(role != null && role.contains(roleKey))) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
