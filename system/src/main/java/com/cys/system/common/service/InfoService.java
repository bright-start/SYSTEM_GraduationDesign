package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;

public interface InfoService {
    Result getUserInfoChart();

    Result getUserMap();

    Result getShopMap(Integer shopId);
}
