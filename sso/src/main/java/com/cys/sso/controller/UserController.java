package com.cys.sso.controller;

import com.cys.sso.pojo.Result;
import com.cys.sso.pojo.UserFingerprint;
import com.cys.sso.service.TokenService;
import com.cys.sso.service.UserService;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private Pattern emailFormatCheck= Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private Pattern phoneFormatCheck = Pattern.compile("^1[34578]\\d{9}$");
    private Pattern usernameFormatCheck1 = Pattern.compile("^[a-zA-Z0-9]{4,10}$");
    private Pattern usernameFormatCheck2 = Pattern.compile("^[\\u4e00-\\u9fa5]{4,8}$");

    @PostMapping("/checkUsername")
    public Result checkUsername(@RequestParam(required = true) String username){
        if(username.length() < 4){
            return new Result().success(200,"为了您的账号安全，请至少输入4个字符");
        }
        if(username.contains("@") && !emailFormatCheck.matcher(username).find()) {
            return new Result().success(200, "邮箱格式错误");
        }
        if(username.length() == 11 && !phoneFormatCheck.matcher(username).find()){
            return new Result().success(200,"请确保这是一个有效的手机号");
        }
        if (!(usernameFormatCheck1.matcher(username).find() || usernameFormatCheck2.matcher(username).find())){
            return new Result().success(200,"账号名必须满足以下格式：4-8位汉字或4-10位英文数字");
        }
        return userService.checkUsername(username);
    }

    @PostMapping("/registry")
    public Result registry(@RequestBody UserFingerprint userFingerprint,HttpServletRequest request) {
        return this.userService.registry(userFingerprint,request);
    }


    @GetMapping("/loadUser")
    public Result loadUser(HttpServletRequest request) {
        Result userInfo = tokenService.getToken(request);
        return userInfo;
    }

    @GetMapping("/logoutSuccess")
    public Result logout(HttpServletRequest request) {
        this.userService.logout(request);
        return (new Result()).success(200, "安全退出");
    }
}
