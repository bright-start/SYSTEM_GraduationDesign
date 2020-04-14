package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.service.AuthUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthUrlController {

    @Autowired
    private AuthUrlService authUrlService;

    @GetMapping("/getAll")
    public Map<String, List<AuthUrl>> listAllRoleAuthUrl(){
        return authUrlService.listAllRoleAuthUrl();
    }

    @GetMapping("/loadAllRole")
    public Result loadAllRole(){
        return authUrlService.getAllRole();
    }

    @GetMapping("/loadHaveAuth")
    public Result loadHaveAuth(@RequestParam Integer roleId){
        return authUrlService.loadHaveAuth(roleId);
    }

    @GetMapping("/loadNoHaveAuth")
    public Result loadNoHaveAuth(@RequestParam Integer roleId){
        return authUrlService.loadNoHaveAuth(roleId);
    }

    @GetMapping("/add")
    public Result addAuth(@RequestParam Integer roleId,@RequestParam Integer authId){
        return authUrlService.addAuth(roleId,authId);
    }

    @GetMapping("/remove")
    public Result removeAuth(@RequestParam Integer roleId,@RequestParam Integer authId){
        return authUrlService.removeAuth(roleId,authId);
    }
}
