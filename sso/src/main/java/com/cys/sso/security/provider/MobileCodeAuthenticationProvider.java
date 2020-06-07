package com.cys.sso.security.provider;

import com.cys.sso.pojo.LoginUser;
import com.cys.sso.pojo.User;
import com.cys.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class MobileCodeAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        String code = (String) authentication.getCredentials();
        if (code == null) {
            throw new BadCredentialsException("验证码不能为空");
        }
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        String _code = (String)redisTemplate.opsForValue().get(mobile);
        if (null == _code) {
            throw new BadCredentialsException("验证码为空");
        }
        User user = userService.findUserByMobile(mobile);
        if(user == null){
            throw new BadCredentialsException("该手机号未注册");
        }
        if (!code.equals(_code)) {
            throw new BadCredentialsException("验证码不正确");
        }

        Collection<GrantedAuthority> collection = new ArrayList<>();
        String roleLevel = null;
        switch (user.getRoleId()) {
            case 0:
                roleLevel = "MEMBER";
                break;
            case 1:
                roleLevel = "SHOP";
                break;
            case 2:
                roleLevel = "ADMIN";
                break;
            case 3:
                roleLevel = "SUPERADMIN";
                break;
            default:
                roleLevel = "TOURISTS";
                break;
        }
        if ("MEMBER".equals(roleLevel) || "SHOP".equals(roleLevel) || "ADMIN".equals(roleLevel)) {
            switch (user.getLevel()) {
                case 0:
                    roleLevel += "_LEVEL1";
                    break;
                case 1:
                    roleLevel += "_LEVEL2";
                    break;
                case 2:
                    roleLevel += "_LEVEL3";
                    break;
                case 3:
                    roleLevel += "_LEVEL4";
                    break;
                case 4:
                    roleLevel += "_LEVEL5";
                    break;
            }
        }
        collection.add(new SimpleGrantedAuthority(roleLevel));
        LoginUser result = new LoginUser(user.getUserId(), user.getUsername(), user.getPassword(), collection, user.getShopId());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
