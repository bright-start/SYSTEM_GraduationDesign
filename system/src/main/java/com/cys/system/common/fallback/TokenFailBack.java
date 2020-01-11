package com.cys.system.common.fallback;

import com.cys.system.common.callcontroller.TokenController;
import com.cys.system.common.common.pojo.Result;
import org.springframework.stereotype.Component;

@Component
public class TokenFailBack implements TokenController {

    @Override
    public Result getToken() {
        return new Result().error(500,"服务器繁忙！");
    }

}