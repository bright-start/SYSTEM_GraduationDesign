package com.cys.sso.service.impl;

import com.cys.sso.config.Config;
import com.cys.sso.pojo.Result;
import com.cys.sso.service.TokenService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisTemplate redisTemplate;

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    public Result buildToken(HttpServletResponse response) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        String token = UUID.randomUUID().toString().replace("-", "");

        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisTemplate.opsForValue().set(token,gson.toJson(loginUser));
        redisTemplate.expire(token,30, TimeUnit.MINUTES);

        Cookie cookie = new Cookie(Config.cookieName,token);
        cookie.setMaxAge(30*60);
        cookie.setPath("/");
//        cookie.setDomain("/");
        response.addCookie(cookie);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator var2 = authorities.iterator();
        if (var2.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority)var2.next();
            String role = authority.getAuthority();
            return !role.contains("ADMIN") && !role.contains("SHOP") ? (new Result()).success(Config.backPage) : (new Result()).success(Config.testPage);
        } else {
            return (new Result()).success(200, "服务器繁忙，请稍后再试");
        }
    }

    @Override
    public Result getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(!(cookies != null && cookies.length > 0)){
            return new Result().success(null);
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if(Config.cookieName.equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }

        if(token == null){
            return new Result().success(null);
        }
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        String tokenJson = (String) redisTemplate.opsForValue().get(token);
        User user = gson.fromJson(tokenJson, User.class);
        if(user != null){
            redisTemplate.expire(token,30,TimeUnit.MINUTES);
        }
        return new Result().success(user);
    }
}
