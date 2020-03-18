package com.cys.system.common.mapper;

import com.cys.system.common.pojo.GoodsSummary;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

public interface CustomGoodsEngineMapper extends ElasticsearchCrudRepository<GoodsSummary,String> {

}
