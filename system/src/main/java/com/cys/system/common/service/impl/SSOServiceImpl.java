package com.cys.system.common.service.impl;

import com.cys.system.common.service.SSOService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息认证中心
 */
@Service
public class SSOServiceImpl implements SSOService {

    @Resource
    private RedisTemplate redisTemplate;

    private final static String COOKIENAME="SYS-TOKEN";

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Map<String, Object> getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(!(cookies != null && cookies.length > 0)){
            return null;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if(COOKIENAME.equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }
        if(token == null){
            return null;
        }

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        String userJson = (String) redisTemplate.opsForValue().get(token);
        Map<String,Object> userMap;
        try {
            userMap = objectMapper.readValue(userJson, Map.class);
        } catch (Exception e) {
            return null;
        }
        redisTemplate.expire("USER" + token, 30, TimeUnit.MINUTES);

        return userMap;
    }
}
