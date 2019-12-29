package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @PostMapping("/login")
    public Result login(@RequestBody(required = true) String username, @RequestBody(required = true) String password) {
        //基于netty或者eureka进入sso中心

        return new Result().success(200, "登陆成功");
    }

    @GetMapping("/logout")
    public Result logout() {
        //基于netty或者eureka进入sso中心
        return new Result().success(200, "成功退出");
    }

}
