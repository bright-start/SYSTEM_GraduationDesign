package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.User;

public interface UserService {
    Result listUser(Integer page, Integer rows, User user);

    Result nouse(Integer[] ids);

    Result load(Integer userId);

    Result modifyPassword(Integer userId,String passwordInfo);
}
