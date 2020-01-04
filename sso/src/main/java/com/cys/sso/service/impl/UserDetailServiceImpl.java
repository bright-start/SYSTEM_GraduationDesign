package com.cys.sso.service.impl;

import com.cys.sso.pojo.LoginUser;
import com.cys.sso.pojo.User;
import com.cys.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Autowired
    private SmsServiceImpl smsService;

    public UserDetails login(LoginUser loginUser){
        if(volidateCode(loginUser.getBindPhone(),loginUser.getCode()))
        loadUserByUsername(loginUser.getUsername());
    }

    private boolean volidateCode(String phone,String code){
        
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        User user = userService.findPasswordByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }

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
                roleLevel = "SUPPERADMIN";
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
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(username, user.getPassword(), collection);
        return userDetails;
    }
}
