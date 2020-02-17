package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.mapper.SpecificationMapper;
import com.cys.system.common.pojo.Specification;
import com.cys.system.common.service.SpecificationService;
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

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Resource
    private SpecificationMapper specificationMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Result listSpecification(Integer page, Integer rows, Specification specification) throws IOException {
        long totalCount = specificationMapper.count(specification);

        if (totalCount > 0) {
            int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);

            if (page >= pageNum) {
                page = pageNum;
            }

            Integer start = (page - 1) * rows;
            PageHelper.startPage(start, rows);
            List<Specification> specifications = specificationMapper.listSpecification(specification);

            if (specifications != null && !specifications.isEmpty()) {
                PageInfo<Specification> pageInfo = new PageInfo<>();
                pageInfo.setTotal(totalCount);
                pageInfo.setList(specifications);


                return new Result().success(pageInfo);
            }
        }
        return new Result().success("无数据");
    }


    @Override
    public Result getSpecificationById(Integer id) {
        Specification specification = specificationMapper.getSpecificationById(id);
        if (specification != null) {
            return new Result().success(specification);
        }
        return new Result().success("无数据");
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteSpecificationByIds(Integer[] ids, Integer shopId) {
        for (Integer id : ids) {
            specificationMapper.deleteSpecificationById(id,shopId);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void insertSpecification(Specification specification) {
        specificationMapper.insertSpecification(specification);
    }

    @Transactional(readOnly = false)
    @Override
    public Result updateSpecificationBySpecification(Specification specification, Integer shopId) throws UnauthorizedException {
        if (shopId == null) {
            specificationMapper.updateSpecificationBySpecification(specification);
            return new Result().success();
        }

        Specification specification_valid = specificationMapper.getSpecificationById(specification.getId());

        if (specification_valid.getShopId() != shopId) {
            throw new UnauthorizedException("拒绝非自定义配置更新");
        } else {
            specificationMapper.updateSpecificationBySpecification(specification);
            return new Result().success();
        }

    }

}
