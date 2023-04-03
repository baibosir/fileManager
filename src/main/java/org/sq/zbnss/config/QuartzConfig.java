package org.sq.zbnss.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sq.zbnss.task.StartOfDayJob;

/**
 * Quartz的相关配置，注册JobDetail和Trigger
 * 注意JobDetail和Trigger是org.quartz包下的，不是spring包下的，不要导入错误
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(StartOfDayJob.class)
                .withIdentity("start_of_day", "start_of_day")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger trigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("start_of_day", "start_of_day")
                .startNow()
                // 每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
        return trigger;
    }
}

