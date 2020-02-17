package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.BrandMapper;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Result listBrand(Integer page, Integer rows, Brand brand) throws IOException {

        String brandJson = (String) redisTemplate.opsForHash().get("brand", "page~" + page);
        if (brandJson == null) {
            synchronized (this) {
                brandJson = (String) redisTemplate.opsForHash().get("brand", "page~" + page);
                if (brandJson == null) {
                    long totalCount = brandMapper.count(brand);

                    if (totalCount > 0) {
                        int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);

                        if (page >= pageNum) {
                            page = pageNum;
                        }

                        Integer start = (page - 1) * rows;
                        PageHelper.startPage(start, rows);
                        List<Brand> brands = brandMapper.listBrand(brand);

                        if (brands != null && !brands.isEmpty()) {
                            PageInfo<Brand> pageInfo = new PageInfo<>();
                            pageInfo.setTotal(totalCount);
                            pageInfo.setList(brands);

                            redisTemplate.opsForHash().put("brand", "page~" + page, objectMapper.writeValueAsString(pageInfo));
                            Random random = new Random();
                            int i = random.nextInt() * 10;
                            redisTemplate.expire("brand" + page, 24 * 60 * 60 + i, TimeUnit.SECONDS);

                            return new Result().success(pageInfo);
                        } else {
                            return new Result().success("无数据");
                        }
                    }
                }
            }
        }
        Random random = new Random();
        int i = random.nextInt() * 10;
        redisTemplate.expire("brand", 24 * 60 * 60 + i, TimeUnit.SECONDS);

        PageInfo<Brand> pageInfo = objectMapper.readValue(brandJson, PageInfo.class);
        return new Result().success(pageInfo);
    }

    @Override
    public Result getBrandById(Integer id) {
        Brand brand = brandMapper.getBrandById(id);
        if (brand != null) {
            return new Result().success(brand);
        }
        return new Result().success("无数据");
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteBrandByIds(Integer[] ids) {
        for (Integer id : ids) {
            brandMapper.deleteBrandById(id);
        }
        redisTemplate.delete("brand");
    }

    @Transactional(readOnly = false)
    @Override
    public void insertBrand(Brand brand) {
        brandMapper.insertBrand(brand);
        redisTemplate.delete("brand");
    }

    @Transactional(readOnly = false)
    @Override
    public Result updateBrandByBrand(Brand brand) {
        brandMapper.updateBrandByBrand(brand);
        redisTemplate.delete("brand");
        return new Result().success();
    }

}
