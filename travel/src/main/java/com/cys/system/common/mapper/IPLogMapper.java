package com.cys.system.common.mapper;

import com.cys.system.common.pojo.IPLog;

import java.util.List;

public interface IPLogMapper {
    void insertIPLog(IPLog ipLog);
    List<IPLog> listIPLog(IPLog ipLog);
}
