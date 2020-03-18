package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.common.pojo.SearchEntity;
import com.cys.system.common.mapper.GoodsEngineMapper;
import com.cys.system.common.mapper.GoodsSummaryMapper;
import com.cys.system.common.pojo.GoodsSummary;
import com.cys.system.common.service.GoodsSummaryService;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class GoodsSummaryServiceImpl implements GoodsSummaryService {

    @Resource
    private GoodsSummaryMapper goodsSummaryMapper;

    @Resource
    private GoodsEngineMapper goodsEngineMapper;


    @Override
    public Result search(SearchEntity searchEntity) {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("goodsName", searchEntity.getGoodsName());
        boolQueryBuilder.must(fuzzyQuery);

        //分页
        queryBuilder.withPageable(PageRequest.of((searchEntity.getPage() - 1), searchEntity.getSize()));

        //搜索文本高亮显示
        queryBuilder.withHighlightFields(new HighlightBuilder.Field("goodsName"));

        //条件排序
        if (searchEntity.getOrderField() == null) {
            queryBuilder.withSort(SortBuilders.fieldSort("productId").order(SortOrder.DESC));
        } else {

            switch (searchEntity.getOrderField()) {
                case "priceUp":
                    queryBuilder.withSort(SortBuilders.fieldSort("goodsPrice").order(SortOrder.DESC));
                    break;
                case "priceDown":
                    queryBuilder.withSort(SortBuilders.fieldSort("goodsPrice").order(SortOrder.ASC));
                    break;
                case "monthSale":
                    queryBuilder.withSort(SortBuilders.fieldSort("goodsMonthSale").order(SortOrder.DESC));
                    break;
                case "recomment":
                    queryBuilder.withSort(SortBuilders.fieldSort("goodsRecomment").order(SortOrder.DESC));
                    break;
                default:
                    queryBuilder.withSort(SortBuilders.fieldSort("productId").order(SortOrder.DESC));
                    break;
            }
        }


        //查询
        queryBuilder.withQuery(boolQueryBuilder);
        Page<GoodsSummary> goodsSummaryPage = goodsEngineMapper.search(queryBuilder.build());


        List<GoodsSummary> goodsSummaryList = new LinkedList<>();
        for (GoodsSummary goodsSummary : goodsSummaryPage) {
            goodsSummaryList.add(goodsSummary);
        }
        if (goodsSummaryList.size() > 0) {
            PageInfo<GoodsSummary> pageInfo = new PageInfo<>(goodsSummaryList);
            pageInfo.setPageNum(searchEntity.getPage());
            pageInfo.setPageSize(goodsSummaryPage.getTotalPages());
            pageInfo.setTotal(goodsSummaryList.size());
            return new Result().success(pageInfo);
        } else {
            return new Result().success(200, "无数据");
        }
    }

    @Override
    public Result importGoodsToEngine() {
        goodsEngineMapper.deleteAll();
        List<GoodsSummary> goodsSummaryList = goodsSummaryMapper.listImportGoods();
        goodsEngineMapper.saveAll(goodsSummaryList);
        return new Result().success("初始化已上架商品引擎成功");
    }
}
