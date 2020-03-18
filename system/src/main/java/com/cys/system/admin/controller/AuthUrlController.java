package com.cys.system.admin.controller;

import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.service.AuthUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthUrlController {

    @Autowired
    private AuthUrlService authUrlService;

    @GetMapping("/getAll")
    public List<AuthUrl> getAllAuthUrl(){
        return authUrlService.getAllAuthUrl();
    }
}
