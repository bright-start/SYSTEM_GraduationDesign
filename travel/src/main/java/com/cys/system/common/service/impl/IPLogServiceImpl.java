package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.IPLogMapper;
import com.cys.system.common.pojo.IPLog;
import com.cys.system.common.service.IPLogService;
import com.cys.system.common.util.AesUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IPLogServiceImpl implements IPLogService {

    @Resource
    private IPLogMapper ipLogMapper;
    
    @Override
    public void insertIPLog(IPLog ipLog) throws Exception {
        ipLog.setIpAddr(AesUtil.encrypt(ipLog.getIpAddr()));
        ipLog.setBrowseTime(AesUtil.encrypt(ipLog.getBrowseTime()));
        ipLog.setBrowseDateDay(AesUtil.encrypt(ipLog.getBrowseDateDay()));
        ipLog.setBrowseDateMonth(AesUtil.encrypt(ipLog.getBrowseDateMonth()));
        ipLog.setBrowseDateYear(AesUtil.encrypt(ipLog.getBrowseDateYear()));
        ipLogMapper.insertIPLog(ipLog);
    }

    @Override
    public Result listIPLog(IPLog ipLog) {
        List<IPLog> ipLogs = ipLogMapper.listIPLog(ipLog);
        if(ipLogs != null &&!ipLogs.isEmpty()){
            return new Result().success(ipLogs);
        }
        return new Result().success("无数据");
    }
}
