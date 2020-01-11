package com.cys.sso.controller;

import com.cys.sso.pojo.Result;
import com.cys.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping({"/redirectPage"})
    public Result LoginSuccessAndRedirectPage(HttpServletResponse response) {
        return tokenService.buildToken(response);
    }

    @GetMapping("/getToken")
    public Result getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return tokenService.getToken(request);
    }


}
