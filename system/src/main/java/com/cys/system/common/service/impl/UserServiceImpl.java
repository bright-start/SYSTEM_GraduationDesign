package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.UserInfoMapper;
import com.cys.system.common.mapper.UserMapper;
import com.cys.system.common.pojo.User;
import com.cys.system.common.pojo.UserFingerprint;
import com.cys.system.common.pojo.UserInfo;
import com.cys.system.common.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Transactional(readOnly = false)
    @Override
    public Result applyUser(UserFingerprint userFingerprint) {
        userMapper.applyUser(userFingerprint.getUser());
        userInfoMapper.saveUserInfo(userFingerprint.getUserInfo());
        return new Result().success();
    }

    @Override
    public Result listUser(Integer page, Integer rows, User user) {
        long count = userMapper.count(user);

        if(count ==0){
            return new Result().success("无数据");
        }
        int pageNum = (int) Math.ceil(count*1.0/rows);

        if(page >= pageNum){
            page = pageNum;
        }
        int start = (page-1)*rows;

        PageHelper.startPage(start,rows);
        List<User> userList = userMapper.listUser(user);
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setList(userList);
        return new Result().success(pageInfo);
    }

    @Override
    public Result getUserById(Integer userId) {
        User user = userMapper.getUserById(userId);
        UserInfo userInfo = userInfoMapper.getUserInfoById(userId);
        UserFingerprint userFingerprint = new UserFingerprint();
        userFingerprint.setUser(user);
        userFingerprint.setUserInfo(userInfo);
        return new Result().success(userFingerprint);
    }
}
