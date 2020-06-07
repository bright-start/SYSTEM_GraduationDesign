package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.MsgMapper;
import com.cys.system.common.pojo.Msg;
import com.cys.system.common.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Resource
    private MsgMapper msgMapper;

    @Override
    public Result loadMsg(Integer userId) {
        List<Msg> msgList = msgMapper.loadMsg(userId);
        return new Result().success(msgList);
    }
}
