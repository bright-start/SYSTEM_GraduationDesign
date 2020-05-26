package com.cys.sso.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.cys.sso.pojo.LoginUser;
import com.cys.sso.pojo.User;
import com.cys.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        String password = (String) authentication.getCredentials();
        if (password == null) {
            throw new BadCredentialsException("密码不能为空");
        }
        User user = userService.findPasswordByUsername(username);
        if (null == user) {
            throw new BadCredentialsException("用户不存在");
        }
        if (!new BCryptPasswordEncoder().matches(password,user.getPassword())) {
            throw new BadCredentialsException("用户名或密码不正确");
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
        LoginUser result = new LoginUser(user.getUserId(), username, user.getPassword(), collection, user.getShopId());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println(this.getClass().getName() + "---supports");
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
