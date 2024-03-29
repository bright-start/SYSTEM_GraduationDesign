//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cys.sso.mapper;

import com.cys.sso.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    Integer registry(User user);

    User findPasswordByUsername(@Param("username") String username);

    Integer checkUsername(String username);

    Integer findStatus(String username);

    void changeStatus(@Param("username") String username,@Param("status") Integer status);

    void updateUserById(@Param("userId") Integer userId,@Param("shopId") Integer shopId);

    User findUserByMobile(String mobile);
}
