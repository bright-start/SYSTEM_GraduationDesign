package com.cys.system.common.mapper;

import com.cys.system.common.pojo.GoodsSummary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GoodsEngineMapper extends ElasticsearchRepository<GoodsSummary, Integer> {
    List<GoodsSummary> findByGoodsNameLike(String goodsName);
}
