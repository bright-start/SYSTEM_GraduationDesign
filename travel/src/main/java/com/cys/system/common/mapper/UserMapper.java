package com.cys.system.common.mapper;

import com.cys.system.common.pojo.User;

import java.util.List;

public interface UserMapper {
    void applyUser(User user);
    User getUserById(Integer userId);
    List<User> listUser(User user);
    long count(User user);
}
