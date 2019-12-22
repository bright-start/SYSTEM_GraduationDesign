package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.BulletinMapper;
import com.cys.system.common.pojo.Bulletin;
import com.cys.system.common.pojo.TimeTask;
import com.cys.system.common.service.BulletinService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BulletinServiceImpl implements BulletinService {

    @Resource
    private BulletinMapper bulletinMapper;

    @Override
    public Result listBulletin(Integer page, Integer rows, Bulletin bulletin) {
        long totalCount = bulletinMapper.count();

        if (totalCount > 0) {
            int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);

            if (page >= pageNum) {
                page = pageNum;
            }

            Integer start = (page - 1) * rows;
            PageHelper.startPage(start, rows);
            List<Bulletin> bulletins = bulletinMapper.listBulletin(bulletin);

            if (bulletins != null && !bulletins.isEmpty()) {
                PageInfo<Bulletin> pageInfo = new PageInfo<>();
                pageInfo.setTotal(totalCount);
                pageInfo.setList(bulletins);
                return new Result().success(pageInfo);
            }
        }
        return new Result().success("无数据");
    }

    @Override
    public List<TimeTask> listTimeTask() {
        List<TimeTask> timeTasks = bulletinMapper.listTimeTask();
        if (timeTasks != null && !timeTasks.isEmpty()) {
            return timeTasks;
        }
        return null;
    }

    @Override
    public Result getBulletinById(Integer id) {
        Bulletin bulletin = bulletinMapper.getBulletinById(id);
        if (bulletin != null) {
            return new Result().success(bulletin);
        }
        return new Result().success("无数据");
    }

    @Override
    public void deleteBulletinById(Integer[] ids) {
        for (Integer id : ids) {
            bulletinMapper.deleteBulletinById(id);
        }
    }

    @Override
    public void insertBulletin(Bulletin bulletin) {
        bulletinMapper.insertBulletin(bulletin);
    }

    @Override
    public Result updateBulletinByBulletin(Bulletin bulletin) {
        Integer status = bulletinMapper.getStatusById(bulletin.getId());
        if (status != null && status == 0) {
            bulletinMapper.updateBulletinByBulletin(bulletin);
            return new Result().success();
        } else {
            return new Result().success("已发布公告不可修改");
        }
    }

    @Override
    public void updateStatusById(Integer id) {
        bulletinMapper.updateStatusById(id);
    }
}
