package com.cys.sso.controller;

import com.cys.sso.pojo.LoginUser;
import com.cys.sso.pojo.Result;
import com.cys.sso.pojo.User;
import com.cys.sso.pojo.UserFingerprint;
import com.cys.sso.service.UserService;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Value("${redirectPage1}")
    private String backPage;
    @Value("${redirectPage2}")
    private String index;
    @Value("${testPage}")
    private String testPage;

    @Value("${cookieName}")
    private String cookieName;

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
    public Result registry(String username, String password, Integer role) {
        UserFingerprint userFingerprint = new UserFingerprint();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoleId(role);
        userFingerprint.setUser(user);
        return this.userService.registry(userFingerprint);
    }

    @RequestMapping({"/redirectPage"})
    public Result redirectPage(HttpServletResponse response) {

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        String token = UUID.randomUUID().toString().replace("-", "");

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisTemplate.opsForValue().set(token,gson.toJson(loginUser));
        redisTemplate.expire(token,30, TimeUnit.MINUTES);

        Cookie cookie = new Cookie(cookieName,token);
        cookie.setMaxAge(30*60);
        cookie.setPath("/");
//        cookie.setDomain("/");
        response.addCookie(cookie);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator var2 = authorities.iterator();
        if (var2.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority)var2.next();
            String role = authority.getAuthority();
            return !role.contains("ADMIN") && !role.contains("SHOP") ? (new Result()).success(this.backPage) : (new Result()).success(this.testPage);
        } else {
            return (new Result()).success(200, "服务器繁忙，请稍后再试");
        }
    }


    @PostMapping("/loadUser")
    public Result loadUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (new Result()).success(username);
    }

    @GetMapping("/logoutSuccess")
    public Result logout(HttpServletRequest request) {
        this.userService.logout(request);
        return (new Result()).success(200, "安全退出");
    }
}
