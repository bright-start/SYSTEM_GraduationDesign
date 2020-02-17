package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.AreaMapper;
import com.cys.system.common.pojo.Area;
import com.cys.system.common.service.AreaService;
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
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Result listArea(Integer page, Integer rows, Area area) throws IOException {


        long totalCount = areaMapper.count(area);

        if (totalCount > 0) {
            int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);

            if (page >= pageNum) {
                page = pageNum;
            }

            int start = (page - 1) * rows;

            PageHelper.startPage(start, rows);
            List<Area> areas = areaMapper.listArea(area);

            if (areas != null && !areas.isEmpty()) {
                PageInfo<Area> pageInfo = new PageInfo<>(areas);
                pageInfo.setTotal(totalCount);
                pageInfo.setPageNum(pageNum);

                return new Result().success(pageInfo);
            }
        }
        return new Result().success("无数据");
    }

    @Override
    public Result getAreaById(Integer id) {
        Area area = areaMapper.getAreaById(id);
        if (area != null) {
            return new Result().success(area);
        }
        return new Result().success("无数据");
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAreaByIds(Integer[] ids) {
        for (Integer id : ids) {
            areaMapper.deleteAreaById(id);
        }
        redisTemplate.delete("brand");
    }

    @Transactional(readOnly = false)
    @Override
    public void insertArea(Area area) {
        areaMapper.insertArea(area);
        redisTemplate.delete("brand");
    }

    @Transactional(readOnly = false)
    @Override
    public Result updateAreaByArea(Area area) {
        areaMapper.updateAreaByArea(area);
        redisTemplate.delete("brand");
        return new Result().success();
    }

}
