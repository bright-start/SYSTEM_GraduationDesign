package com.cys.system.admin.controller;

import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.service.AuthUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
