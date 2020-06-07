package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.service.MsgService;
import com.cys.system.common.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private SSOService ssoService;

    @GetMapping("/load")
    public Result loadMsg(HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if(userMap != null){
            Integer userId = (Integer)userMap.get("shopId");
            return msgService.loadMsg(userId);
        }
        throw new UnauthorizedException();
    }
}
