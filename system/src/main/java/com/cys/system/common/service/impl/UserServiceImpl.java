package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.UserMapper;
import com.cys.system.common.pojo.Order;
import com.cys.system.common.pojo.OrderItem;
import com.cys.system.common.pojo.User;
import com.cys.system.common.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional(readOnly = false)
    @Override
    public Result nouse(Integer[] ids) {
        for (Integer id : ids) {
            userMapper.nouse(id);
        }
        return new Result().success(200, "禁用成功");
    }

    @Override
    public Result listUser(Integer page, Integer rows, User user) {
        long count = userMapper.count(user);

        if (count == 0) {
            return new Result().success("无数据");
        }
        int pageNum = (int) Math.ceil(count * 1.0 / rows);

        if (page >= pageNum) {
            page = pageNum;
        }
        int start = (page - 1) * rows;

        PageHelper.startPage(start, rows);
        List<Map> userList = userMapper.listUser(user);

        PageInfo<Map> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setPageNum(pageNum);
        pageInfo.setList(userList);
        return new Result().success(pageInfo);
    }

    @Override
    public Result load(Integer userId) {
        Map map = userMapper.load(userId);
        map.put("password",null);
        return new Result().success(map);
    }

    @Transactional(readOnly = false)
    @Override
    public Result modifyPassword(Integer userId,String passwordInfo) {
        Map map = OnlyOneClassConfig.gson.fromJson(passwordInfo, Map.class);
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String)map.get("newPassword");
        Integer phone = (Integer) map.get("phone");
        User user = userMapper.volidUser(userId,oldPassword);
        if(user != null){
            user.setPassword(newPassword);
            userMapper.updatePassword(user);
            return new Result().success();
        }
        return new Result().success(200,"旧密码输入错误");
    }
}
