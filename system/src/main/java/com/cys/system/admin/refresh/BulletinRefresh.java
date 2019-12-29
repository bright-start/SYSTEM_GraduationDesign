package com.cys.system.admin.refresh;

import com.cys.system.common.config.RefreshCenter;
import com.cys.system.common.pojo.TimeTask;
import com.cys.system.common.service.BulletinService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 公告刷新插件
 */
@Component
public class BulletinRefresh extends RefreshCenter {

    @Autowired
    private BulletinService bulletinService;

    @Override
    protected int getTypeId() {
        return 1;
    }

    @Override
    protected void timeTask() {
        List<TimeTask> timeTasks = bulletinService.listTimeTask();
        if (timeTasks != null && !timeTasks.isEmpty()) {
            for (TimeTask timeTask : timeTasks) {
                if (timeTask.getEnable() == 1 && !"null".equals(timeTask.getTime())) {
                    try {
                        if (TimeConverter.StringToDate(timeTask.getTime()).before(new Date())) {
                            bulletinService.updateStatusById(timeTask.getId());
                        }
                    } catch (Exception e) {
//                         日志记录
                    }
                }
            }
        }
    }
}
