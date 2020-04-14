package com.cys.system.common.mapper;

import com.cys.system.common.pojo.SysLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysLogMapper {

    SysLog getUserInfo(@Param("year") String year, @Param("month") String month);
}
