package com.cys.system.common.mapper;

import com.cys.system.common.pojo.AuthUrl;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuthUrlRoleMapper {

    @Select("SELECT\n" +
            "\"public\".permission.\"permission_url\",\n" +
            "\"public\".permission.\"permission_id\"\n" +
            "FROM\n" +
            "\"public\".permission\n" +
            "JOIN \"public\".\"role_permission\"\n" +
            "ON \"public\".permission.\"permission_id\" = \"public\".\"role_permission\".\"permission_id\"\n" +
            "WHERE\n" +
            "\"public\".\"role_permission\".\"role_id\"=#{roleId}")
    List<AuthUrl> loadAuth(Integer roleId);

    @Insert("insert into role_permission(role_id,permission_id) values(#{roleId},#{authId})")
    void addAuth(@Param("roleId") Integer roleId, @Param("authId") Integer authId);

    @Delete("delete from role_permission where role_id=#{roleId} and permission_id=#{authId}")
    void removeAuth(@Param("roleId") Integer roleId, @Param("authId") Integer authId);
}
