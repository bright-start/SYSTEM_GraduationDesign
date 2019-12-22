package com.cys.system.common.config;

import com.cys.system.common.mapper.RefreshMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;

/**
 * 刷新中心
 */
@Configuration
@EnableScheduling
public abstract class RefreshCenter implements SchedulingConfigurer {

    @Resource
    private RefreshMapper refreshMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            this.timeTask();
        }, triggerContext -> {
            String cron = refreshMapper.getRefreshTimeByType(this.getTypeId());
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }
    protected abstract void timeTask();
    protected abstract int getTypeId();
}
