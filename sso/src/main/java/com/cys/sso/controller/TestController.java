package com.cys.sso.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    public TestController() {
    }

    @PostMapping({"/test"})
    public String test() {
        return "admin-success";
    }
}
