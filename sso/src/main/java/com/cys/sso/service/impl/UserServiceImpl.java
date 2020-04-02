package com.cys.sso.service.impl;

import com.cys.sso.config.Config;
import com.cys.sso.mapper.ShopMapper;
import com.cys.sso.mapper.UserInfoMapper;
import com.cys.sso.mapper.UserMapper;
import com.cys.sso.pojo.*;
import com.cys.sso.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.cys.sso.utils.TimeConverter;
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

    @Resource
    private ShopMapper shopMapper;

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
        userInfo.setLoginTime(userInfo.getLastLoginTime());
        userInfoMapper.saveUserInfo(userInfo);
        if (userId != null) {
            return (new Result()).success(200, "注册成功");
        } else {
            return new Result().success(200, "注册失败");
        }
    }

    @Transactional(
            readOnly = false
    )
    @Override
    public Result shopRegistry(Shop shop, HttpServletRequest request) {
        if(shopMapper.existShop(shop.getShopName()) != null){
            return new Result().success(200,"店铺昵称已被使用");
        }
        shop.setCreatetime(TimeConverter.DateToString(new Date()));
        shop.setStatus(0);
        shopMapper.saveShopInfo(shop);

        //检查该账号是否已注册
        User user = userMapper.findPasswordByUsername(shop.getUsername());
        if(user != null) {
            if (!user.getPassword().equals((new BCryptPasswordEncoder()).encode(shop.getPassword()))) {
                return new Result().success("会员账号密码错误");
            }

            userMapper.updateUserById(user.getUserId(),shop.getShopId());
        }else{

            //未注册则注册
            User newUser = new User();
            newUser.setUsername(shop.getUsername());
            newUser.setPassword((new BCryptPasswordEncoder()).encode(shop.getPassword()));
            newUser.setBindPhone(shop.getBindPhone());
            newUser.setRoleId(shop.getRoleId());
            newUser.setShopId(shop.getShopId());
            newUser.setStatus(0);
            newUser.setLevel(0);
            newUser.setImage(shop.getLogoPath());
            userMapper.registry(newUser);

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(newUser.getUserId());
            userInfo.setRegistryTime(TimeConverter.DateToString(new Date()));
            userInfo.setLastLoginTime(userInfo.getRegistryTime());
            userInfo.setLoginTime(userInfo.getLastLoginTime());
            userInfoMapper.saveUserInfo(userInfo);
        }

        return new Result().success("开始申请店铺,一个工作日返回审核结果，请耐心等待");
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

    @Override
    public void updateLoginTimeById(Integer userId) {
        String loginTime = TimeConverter.DateToString(new Date());
        userInfoMapper.updateLoginTimeById(loginTime,userId);
    }
}
