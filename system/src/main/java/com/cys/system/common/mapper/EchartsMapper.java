package com.cys.system.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EchartsMapper {
    @Select("select echarts_data from echarts where echarts_type = #{echartsType}")
    String getEchartsTemplateByType(@Param("echartsType") Integer type);
}
