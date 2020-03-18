package com.cys.system.common.callcontroller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.callcontroller.fallback.TokenFailBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(value = "SSO",path = "/sso",fallback = TokenFailBack.class)
public interface TokenController {
    @GetMapping(value = "/getToken",consumes = "application/json")
    Result getToken(@RequestBody String token);

}
