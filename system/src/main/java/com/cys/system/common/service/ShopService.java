package com.cys.system.common.service;

import com.aliyuncs.exceptions.ClientException;
import com.cys.system.common.common.pojo.Result;

public interface ShopService {
    Result listApplyShop(Integer page, Integer rows);

    Result examine(Integer[] ids, Integer status);
}
