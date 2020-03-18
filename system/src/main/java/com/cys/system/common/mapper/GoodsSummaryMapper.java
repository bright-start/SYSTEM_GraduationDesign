package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Goods;
import com.cys.system.common.pojo.GoodsSummary;

import java.util.List;

public interface GoodsSummaryMapper {
    List<GoodsSummary> listImportGoods();

    GoodsSummary findGoodsSummaryById(Integer id);
}
