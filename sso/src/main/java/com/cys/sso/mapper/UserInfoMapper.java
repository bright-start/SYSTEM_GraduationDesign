//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cys.sso.mapper;

import com.cys.sso.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;

public interface UserInfoMapper {
    @Insert({"insert into user_info values(#{userId},#{registryTime},#{lastLoginTime},#{phoneType},#{phoneIp},#{area})"})
    void saveUserInfo(UserInfo userInfo);
}
