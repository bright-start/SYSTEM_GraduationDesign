package com.cys.system.common.mapper;

import com.cys.system.common.pojo.AuthUrl;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuthUrlMapper {
    @Select("select * from permission")
    List<AuthUrl> getAllAuthUrl();
}
