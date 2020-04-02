package com.cys.system.common.mapper;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Bulletin;
import com.cys.system.common.pojo.TimeTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BulletinMapper {
    List<Bulletin> listBulletin(Bulletin bulletin);

    List<TimeTask> listTimeTask();

    Bulletin getBulletinById(Integer id);

    Integer getStatusById(Integer id);

    long count();

    void deleteBulletinById(Integer id);

    void updateBulletinByBulletin(Bulletin bulletin);

    void updateStatusById(@Param("id") Integer id);

    void insertBulletin(Bulletin bulletin);

    List<Bulletin> loadBulletinList();
}
