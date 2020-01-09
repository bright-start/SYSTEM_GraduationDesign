package com.cys.sso.mapper;

import com.cys.sso.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;

public interface UserInfoMapper {
    void saveUserInfo(UserInfo userInfo);
}
