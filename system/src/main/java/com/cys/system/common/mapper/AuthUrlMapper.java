package com.cys.system.common.mapper;

import com.cys.system.common.pojo.AuthUrl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthUrlMapper {
    @Select("select * from permission")
    List<AuthUrl> getAllAuthUrl();

    @Select("SELECT\n" +
            "\"public\".permission.\"permission_url\"\n" +
            "FROM\n" +
            "\"public\".permission\n" +
            "JOIN \"public\".\"role_permission\"\n" +
            "ON \"public\".permission.\"permission_id\" = \"public\".\"role_permission\".\"permission_id\"\n" +
            "WHERE\n" +
            "\"public\".\"role_permission\".\"role_id\"=#{roleId}")
    List<AuthUrl> listAuthUrlForRole(@Param("roleId") Integer roleId);
}
