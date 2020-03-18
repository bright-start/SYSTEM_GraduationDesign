package com.cys.search.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class CookValidController {

    @Resource
    private RedisTemplate redisTemplate;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    protected String getTokenByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (!(cookies != null && cookies.length > 0)) {
            return null;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if ("SYS-TOKEN".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            return null;
        }
        return token;
    }

    protected Integer getUserIdByCookie(HttpServletRequest request) {
        String token = getTokenByCookie(request);
        if (token == null) {
            return null;
        }

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        String userJson = (String) redisTemplate.opsForValue().get(token);
        if (userJson != null) {
            Map userMap = gson.fromJson(userJson, Map.class);
            return (Integer) userMap.get("userId");
        }
        return null;
    }

    protected String getRoleByCookie(HttpServletRequest request) {
        String token = getTokenByCookie(request);
        if (token == null) {
            return null;
        }
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        String userJson = (String) redisTemplate.opsForValue().get(token);
        if (userJson != null) {
            Map userMap = gson.fromJson(userJson, Map.class);
            if (userMap != null && !userMap.isEmpty()) {
                List list = (List) userMap.get("authorities");
                if (list != null && !list.isEmpty()) {
                    Map map = (Map) list.get(0);
                    if (map != null && !map.isEmpty()) {
                        String role = (String) map.get("role");
                        return role;
                    }
                }
            }
        }
        return null;
    }

    protected List<String> getNoPayCode(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (!(cookies != null && cookies.length > 0)) {
            return null;
        }
        List<String> noPayList = new LinkedList<>();
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains("code_")) {
                noPayList.add(cookie.getName());
            }
        }
        return noPayList;
    }
}
