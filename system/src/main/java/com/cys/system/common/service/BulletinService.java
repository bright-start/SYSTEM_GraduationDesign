package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Bulletin;
import com.cys.system.common.pojo.TimeTask;

import java.util.List;

public interface BulletinService {
    Result listBulletin(Integer page, Integer rows, Bulletin bulletin);

    List<TimeTask> listTimeTask();

    Result getBulletinById(Integer id);

    void deleteBulletinById(Integer[] id);

    void insertBulletin(Bulletin bulletin);

    Result updateBulletinByBulletin(Bulletin bulletin);

    void updateStatusById(Integer id);

    Result loadBulletinList();

    Result updateStatus(Integer id);
}
