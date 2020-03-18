package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.SearchEntity;
import com.cys.system.common.pojo.Goods;

public interface GoodsSummaryService {

    Result importGoodsToEngine();

    Result search(SearchEntity searchEntity);
}
