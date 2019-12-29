package com.cys.system.common.mapper;

import com.cys.system.common.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper {
    @Insert("insert into user_info values(#{userId},#{registryTime},#{lastLoginTime},#{phoneType},#{phoneIp},#{area})")
    void saveUserInfo(UserInfo userInfo);

}
