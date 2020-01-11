package com.cys.system.common.callcontroller;

import com.cys.system.common.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "SSO",path = "/sso")
public interface SSOController {
    @GetMapping("/loadUser")
    Result loadUser();

    @GetMapping("/logoutSuccess")
    Result logoutSuccess();
}
