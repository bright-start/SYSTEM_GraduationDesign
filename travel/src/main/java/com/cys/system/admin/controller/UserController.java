package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.pojo.User;
import com.cys.system.common.pojo.UserFingerprint;
import com.cys.system.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 本功能只能用户操作
     * @return
     */
    @PostMapping("/applyUser")
    public Result addUser(@RequestBody UserFingerprint userFingerprint){
        return userService.applyUser(userFingerprint);
    }


    @GetMapping("/findUserById")
    public Result findUserById(Integer userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/list")
    public Result listUser(Integer page, Integer rows, @RequestBody User user) throws InvalidRequestException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if(rows == 10 || rows == 20 || rows == 30 || rows == 40|| rows == 50){
            return userService.listUser(page,rows,user);
        }else {
            throw new InvalidRequestException();
        }

    }
}
