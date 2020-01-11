package com.cys.system.admin.controller;

import com.cys.system.common.callcontroller.SSOController;
import com.cys.system.common.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class LoginController {



    /**
     *
     * 在tokenfilter和logincontroller中都注入ssocontroller 会发生一个循环注入，暂时搁置，尚不清楚原因
     *
     *
     * The dependencies of some of the beans in the application context form a cycle:
     *
     *    loginController (field private com.cys.system.common.callcontroller.SSOController com.cys.system.admin.controller.LoginController.ssoController)
     * ┌─────┐
     * |  com.cys.system.common.callcontroller.SSOController
     * ↑     ↓
     * |  org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration$EnableWebMvcConfiguration
     * ↑     ↓
     * |  webMvcConfig (field private com.cys.system.common.filter.TokenFilter com.cys.system.common.filter.WebMvcConfig.tokenFilter)
     * ↑     ↓
     * |  tokenFilter (field private com.cys.system.common.callcontroller.SSOController com.cys.system.common.filter.TokenFilter.ssoController)
     * ↑     ↓
     * |  mvcResourceUrlProvider
     * └─────┘
     */
//    @Resource
//    private SSOController ssoController;
//
//    @PostMapping("/loadLoginUser")
//    public Result loadLoginUser() {
//        //基于netty或者eureka进入sso中心
//        return ssoController.loadUser();
//    }
//
//    @GetMapping("/logout")
//    public Result logout() {
//        //基于netty或者eureka进入sso中心
//        return ssoController.logoutSuccess();
//    }

}
