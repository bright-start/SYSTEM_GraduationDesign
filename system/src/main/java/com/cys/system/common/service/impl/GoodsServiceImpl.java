package com.cys.system.common.service.impl;

import com.cys.system.common.common.mq.MsgSender;
import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.config.Config;
import com.cys.system.common.mapper.GoodsEngineMapper;
import com.cys.system.common.mapper.GoodsMapper;
import com.cys.system.common.mapper.GoodsSummaryMapper;
import com.cys.system.common.mapper.ProductMapper;
import com.cys.system.common.pojo.Goods;
import com.cys.system.common.pojo.GoodsSummary;
import com.cys.system.common.pojo.Product;
import com.cys.system.common.service.GoodsService;
import com.cys.system.common.util.TimeConverter;
import com.cys.system.common.util.TimeFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private GoodsSummaryMapper goodsSummaryMapper;

    @Resource
    private GoodsEngineMapper goodsEngineMapper;

    @Autowired
    private MsgSender msgSender;

    @Transactional(readOnly = false)
    @Override
    public Result recomment(Integer id,Integer status) {
        goodsMapper.recomment(id,status);
        if(status == 1){
            return new Result().success(1);
        }else {
            return new Result().success(-1);
        }

    }

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

    @Transactional(readOnly = false)
    @Override
    public Result examine(Integer id, Integer status) {
        int successFlag = goodsMapper.updateStatus(id, status);
        if (successFlag == 0) {
            return new Result().success("审核状态更新失败");
        }
        //下架
        if(status == 6) {
            goodsEngineMapper.deleteById(id);
        }

        msgSender.sendToShop(id,status);

        return new Result().success("审核状态更新成功");
    }

    @Transactional(readOnly = false)
    @Override
    public Result examineSelected(Integer[] ids, Integer status) {
        List<Integer> failList = new ArrayList<>();
        for (Integer id : ids) {
            int successFlag = goodsMapper.updateStatus(id, status);
            if (successFlag == 0) {
                failList.add(id);
                continue;
            }

            msgSender.sendToShop(id,status);
        }
        if (!failList.isEmpty()) {
            return new Result().error(400, "部分审核状态更新失败", failList.toString());
        }
        return new Result().success("审核状态更新成功" + failList);
    }

    @Transactional(readOnly = false)
    @Override
    public Result addGoods(Goods goods, Integer shopId) {

        goods.setShopId(shopId);
        goods.setCreateTime(TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S));
        goods.setStatus(0);
        goodsMapper.addGoods(goods);
        List<Product> productList = goods.getProductList();
        for (Product product : productList) {
            product.setGoodsId(goods.getId());
            product.setProductName(goods.getGoodsName());
            product.setProductImage(goods.getGoodsImages().split(",")[1]);
            productMapper.addProduct(product);
        }
        return new Result().success("添加成功");
    }

    @Transactional(readOnly = false)
    @Override
    public Result updateGoodsByGoods(Goods goods) {
        goods.setCreateTime(TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S));
        goodsMapper.updateGoods(goods);
        List<Product> productList = goods.getProductList();
        for (Product product : productList) {
            product.setGoodsId(goods.getId());
            productMapper.updateProduct(product);
        }
        return new Result().success("更新商品成功");
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteGoodsById(Integer id) {
        //下架则从商品搜索引擎中删除
        goodsEngineMapper.deleteById(id);

        goodsMapper.deleteGoodsById(id);
        productMapper.deleteProductByGoodsId(id);
        new Result().success("删除商品成功");
    }

    @Transactional(readOnly = false)
    @Override
    public Result updateStatus(Integer[] ids, Integer status) {
        List<Integer> errorId = null;
        for (Integer id : ids) {
            Integer curStatus = goodsMapper.getStatusById(id);
            //未上架产品只能更新上架商品状态
            if (curStatus == null || (curStatus < 4 && status > 4) || curStatus == 6) {
                errorId.add(id);
                continue;
            }

            goodsMapper.updateStatus(id, status);
        }

        if (errorId != null) {
            return new Result().error(400, "部分商品状态更新失败", errorId);
        }

        return new Result().success("商品状态更新成功");
    }

    @Override
    public Product saleProduct() {
        return null;
    }

    @Override
    public Result transactionAnalysis() {
        return null;
    }

    @Override
    public List<Map> listDeleteGoods() {
        return goodsMapper.listDeleteGoods();
    }

    @Override
    public Result findGoodGoods() {
        List<Map> goodGoods = goodsMapper.findGoodGoods();
        if(goodGoods != null && goodGoods.size()> 0){
            return new Result().success(goodGoods);
        }
        return new Result().success();
    }

}
