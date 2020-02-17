package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.pojo.User;
import com.cys.system.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public Result listUser(Integer page, Integer rows, @RequestBody User user) throws InvalidRequestException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return userService.listUser(page, rows, user);
        } else {
            throw new InvalidRequestException("参数错误");
        }
    }

    /**
     * 更改用户账号状态为禁用
     */
    @DeleteMapping("/nouse")
    public Result nouse(Integer[] ids) throws InvalidRequestException {
        if (ids == null && ids.length == 0) {
            throw new InvalidRequestException("参数错误");
        }
        return userService.nouse(ids);
    }

}
