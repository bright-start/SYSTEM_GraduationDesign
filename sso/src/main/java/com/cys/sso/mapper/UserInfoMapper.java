package com.cys.sso.mapper;

import com.cys.sso.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {
    void saveUserInfo(UserInfo userInfo);
    void updateLoginTimeById(@Param("loginTime")String loginTime, @Param("userId")Integer userId);
}
