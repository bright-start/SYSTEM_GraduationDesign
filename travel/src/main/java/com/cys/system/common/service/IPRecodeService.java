package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.IPRecord;


public interface IPRecodeService {
    Result findAllIPRecode(Integer start,Integer rows,IPRecord ipRecord) throws Exception;
    IPRecord getIPRecode(String ip);
    void recodeIP(String ip) throws Exception;
    void updateIPRecord(IPRecord ipRecord);
    void refreshRecode();
}
