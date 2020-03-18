package com.cys.sso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sso")
public class TestController {
    public TestController() {
    }

    @GetMapping({"/test"})
    public String test(HttpServletRequest request) {
        return "admin-success";
    }
}
