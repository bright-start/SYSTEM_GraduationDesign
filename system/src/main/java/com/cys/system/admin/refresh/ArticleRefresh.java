package com.cys.system.admin.refresh;

import com.cys.system.common.config.RefreshCenter;
import com.cys.system.common.pojo.TimeTask;
import com.cys.system.common.service.ArticleService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 文章刷新插件
 */
@Component
public class ArticleRefresh extends RefreshCenter  {

    @Autowired
    private ArticleService articleService;

    @Override
    protected int getTypeId() {
        return 2;
    }

    @Override
    protected void timeTask() {
        List<TimeTask> timeTasks = articleService.listTimeTask();
        if (timeTasks != null && !timeTasks.isEmpty()) {
            for (TimeTask timeTask : timeTasks) {
                if (timeTask.getEnable() == 1 && !"null".equals(timeTask.getTime())) {
                    try {
                        if (TimeConverter.StringToDate(timeTask.getTime()).before(new Date())) {
                            articleService.updateStatusById(timeTask.getId());
                        }
                    } catch (Exception e) {
//                         日志记录
                    }
                }
            }
        }
    }
}
