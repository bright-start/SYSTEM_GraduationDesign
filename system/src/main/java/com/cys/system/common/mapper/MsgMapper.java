package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Msg;

import java.util.List;

public interface MsgMapper {
    void storeMessage(Msg msg);

    List<Msg> loadMsg(Integer userId);
}
