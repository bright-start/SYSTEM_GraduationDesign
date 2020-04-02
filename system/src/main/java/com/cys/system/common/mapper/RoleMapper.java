package com.cys.system.common.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    @Select("select * from role")
    List<Map> listRole();
}
