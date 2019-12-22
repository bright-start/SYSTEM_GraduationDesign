package com.cys.system.common.service.impl;

import com.cys.system.common.pojo.User;
import com.cys.system.common.service.SSOService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息认证中心
 */
@Service
public class SSOServiceImpl implements SSOService {

    @Resource
    private RedisTemplate redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SSOServiceImpl() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
    }

    @Override
    public User getUserByToken(String token) {
        String userJson = (String) redisTemplate.opsForValue().get("USER" + token);
        User user = null;
        try {
            user = objectMapper.readValue(userJson, User.class);
        }catch (Exception e){
            return null;
        }
        redisTemplate.expire("USER"+token,30, TimeUnit.MINUTES);
        return user;
    }

    @Override
    public String SaveUser(User user) {
        String token = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("userToken",token);

        redisTemplate.opsForValue().set("USER"+token,user);
        redisTemplate.expire("USER"+token,30,TimeUnit.MINUTES);

        return token;
    }
}
