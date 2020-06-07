package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.User;
import com.cys.system.common.service.SSOService;
import com.cys.system.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SSOService ssoService;

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
    @PutMapping("/nouse")
    public Result nouse(Integer[] ids) throws InvalidRequestException {
        if (ids == null) {
            throw new InvalidRequestException("参数错误");
        }
        return userService.nouse(ids);
    }

    @GetMapping("/load")
    public Result load(HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if(!(userMap != null && !userMap.isEmpty())) {
            throw new UnauthorizedException();
        }
        Integer userId = (Integer) userMap.get("userId");
        if(userId == null){
            throw new UnauthorizedException();
        }
        return userService.load(userId);
    }

    @PostMapping("/modifyPassword")
    public Result modifyPassword(@RequestBody String passwordInfo,HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if(!(userMap != null && !userMap.isEmpty())) {
            throw new UnauthorizedException();
        }
        Integer userId = (Integer) userMap.get("userId");
        if(userId == null){
            throw new UnauthorizedException();
        }
        return userService.modifyPassword(userId,passwordInfo);
    }

    @PostMapping("/createAdmin")
    public Result createAdmin(@RequestBody User user){
        return userService.createAdmin(user);
    }

}
