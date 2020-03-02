package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.GoodsMapper;
import com.cys.system.common.pojo.Goods;
import com.cys.system.common.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Result listGoods(Integer page, Integer rows, Goods goods) {

        long totalCount = goodsMapper.count(goods);

        if (totalCount > 0) {
            int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);

            if (page >= pageNum) {
                page = pageNum;
            }

            int start = (page - 1) * rows;
            PageHelper.startPage(start, rows);
            List<Goods> goodsList = goodsMapper.listGoods(goods);

            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            pageInfo.setPageNum(page);
            pageInfo.setTotal(totalCount);
            return new Result().success(pageInfo);
        }
        return new Result().success("无数据");
    }

    @Override
    public Result findGoodsById(Integer id) {
        Goods goods = goodsMapper.findGoodsById(id);
        return new Result().success(goods);
    }

    @Override
    public Result examine(Integer id, Integer status) {
        int successFlag = goodsMapper.examine(id, status);
        if (successFlag == 0) {
            return new Result().success("审核状态更新失败");
        }
        return new Result().success("审核状态更新成功");
    }

    @Override
    public Result examineSelected(Integer[] ids, Integer status) {
        List<Integer> failList = new ArrayList<>();
        for (Integer id : ids) {
            int successFlag = goodsMapper.examine(id, status);
            if (successFlag == 0) {
                failList.add(id);
            }
        }
        return new Result().success(failList);
    }
}
