package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.IPLog;


public interface IPLogService {
    void insertIPLog(IPLog ipLog) throws Exception;
    Result listIPLog(IPLog ipLog);
}
