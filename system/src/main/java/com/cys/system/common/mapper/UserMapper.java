package com.cys.system.common.mapper;

import com.cys.system.common.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    List<Map> listUser(User user);

    long count(User user);

    void nouse(@Param("userid") Integer id);

    User getUserById(Integer userId);

    Map load(Integer userId);

    User volidUser(@Param("userId") Integer userId, @Param("password") String oldPassword);

    void updatePassword(User user);
}
