package com.cys.sso.service.impl;

import com.cys.sso.config.Config;
import com.cys.sso.mapper.UserInfoMapper;
import com.cys.sso.mapper.UserMapper;
import com.cys.sso.pojo.Result;
import com.cys.sso.pojo.User;
import com.cys.sso.pojo.UserFingerprint;
import com.cys.sso.pojo.UserInfo;
import com.cys.sso.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(
        readOnly = true, rollbackFor = Exception.class
)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    public User findPasswordByUsername(String username) {
        return this.userMapper.findPasswordByUsername(username);
    }

    public void logout(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie[] var3 = cookies;
        int var4 = cookies.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Cookie cookie = var3[var5];
            if (Config.cookieName.equals(cookie.getName())) {
                String token_value = cookie.getValue();
                RedisSerializer redisSerializer = new StringRedisSerializer();
                this.redisTemplate.setKeySerializer(redisSerializer);
                this.redisTemplate.expire(token_value, 0L, TimeUnit.SECONDS);
                cookie.setMaxAge(0);
            }
        }

    }

    @Transactional(
            readOnly = false
    )
    public Result registry(UserFingerprint userFingerprint, HttpServletRequest request) {
        User user = userFingerprint.getUser();
        if (user.getRoleId() == 0 && user.getImage() == null) {
        }

        if (user.getRoleId() == 1 && user.getImage() == null) {
        }

        user.setStatus(0);
        user.setLevel(0);
        user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
        Integer userId = this.userMapper.registry(userFingerprint.getUser());
        UserInfo userInfo = userFingerprint.getUserInfo();
        userInfo.setUserId(userId);
        userInfo.setIpAddr(request.getRemoteAddr());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userInfo.setRegistryTime(simpleDateFormat.format(new Date()));
        userInfo.setLastLoginTime(userInfo.getRegistryTime());
        userInfoMapper.saveUserInfo(userInfo);
        if (userId != null) {
            return (new Result()).success(200, "注册成功");
        } else {
            return new Result().success(200, "注册失败");
        }
    }

    @Override
    public Result checkUsername(String username) {
        Integer userId = userMapper.checkUsername(username);
        if (userId != null) {
            return new Result().success(200, "该昵称已被使用");
        } else {
            return new Result().success(200, "OK");
        }
    }

    @Override
    public Integer isLogin(String username) {
        return userMapper.findStatus(username);
    }

    @Transactional(readOnly = false)
    @Override
    public void changeStatus(String username,Integer status) {
        userMapper.changeStatus(username,status);
    }
}
