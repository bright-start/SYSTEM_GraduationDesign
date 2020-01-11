package com.cys.system.common.callcontroller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.fallback.TokenFailBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "SSO",path = "/sso",fallback = TokenFailBack.class)
public interface TokenController {
    @GetMapping("/getToken")
    Result getToken();

}
