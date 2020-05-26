package com.cys.search.service.impl;

import com.cys.search.pojo.Result;
import com.cys.search.service.SSOService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SSOServiceImpl implements SSOService {

    @Resource
    private RedisTemplate redisTemplate;

    private final static String COOKIENAME = "SYS-TOKEN";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> getUser(HttpServletRequest request) {

        String token = getTokenByCookie(request);
        if (token == null) {
            return null;
        }
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        String userJson = (String) redisTemplate.opsForValue().get(token);
        if (userJson == null) {
            return null;
        }
        Map<String, Object> userMap;
        try {
            userMap = objectMapper.readValue(userJson, Map.class);
        } catch (Exception e) {
            return null;
        }
        redisTemplate.expire("USER" + token, 30, TimeUnit.MINUTES);

        return userMap;
    }

    @Override
    public String getRoleByCookie(HttpServletRequest request){
        Map<String, Object> userMap = getUser(request);
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
        return null;
    }

    @Override
    public Integer getUserIdByCookie(HttpServletRequest request) {
        Map<String, Object> userMap = getUser(request);
        if(userMap !=null && userMap.size() > 0){
            return (Integer)userMap.get("userId");
        }
        return null;
    }


    @Override
    public String getTokenByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (!(cookies != null && cookies.length > 0)) {
            return null;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if (COOKIENAME.equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        return token;
    }

    @Override
    public List<String> getNoPayCode(HttpServletRequest request) {
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

    @Override
    public Result getTTL(String payCode) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        long ttl = redisTemplate.getExpire(payCode,TimeUnit.SECONDS);
        if(ttl <= 0){
            ttl = 0;
        }
        return new Result().success(ttl);
    }
}
