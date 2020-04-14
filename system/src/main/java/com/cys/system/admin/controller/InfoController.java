package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.service.InfoService;
import com.cys.system.common.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private InfoService infoService;

    @Autowired
    private SSOService ssoService;

    @GetMapping("/registration_map")
    public Result getRegistrationMap(){
        return infoService.getUserInfoChart();
    }

    @GetMapping("/user_map")
    public Result getUserMap (){
        return infoService.getUserMap();
    }

    @GetMapping("/shop_map")
    public Result getShopMap(HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        Integer shopId = (Integer) userMap.get("shopId");
        return infoService.getShopMap(shopId);
    }

}
