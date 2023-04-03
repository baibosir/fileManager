package org.sq.zbnss.uitl;

import org.quartz.*;
import org.sq.zbnss.base.QuartzVo;

public class QuartzUtils {

    /**
     * 创建定时任务 定时任务创建之后默认启动状态
     * @param scheduler   调度器
     * @param quartzBean  定时任务信息类
     * @throws Exception
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzVo quartzBean){
        try {
            //获取到定时任务的执行类  必须是类的绝对路径名称
            //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName("com.leenleda.data.synchronism.common.task.AutomaticTask");
            // 构建定时任务信息 使用id作为任务唯一值，在执行时可以拿到。
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getId()).withDescription(quartzBean.getJobName()).build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            // 构建触发器trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getId()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            System.out.println("定时任务类路径出错：请输入类的绝对路径");
        } catch (SchedulerException e) {
            System.out.println("创建定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据任务Id暂停定时任务
     * @param scheduler  调度器
     * @param jobId    定时任务名称
     * @throws SchedulerException
     */
    public static void pauseScheduleJob(Scheduler scheduler, String jobId){
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("暂停定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据任务Id恢复定时任务
     * @param scheduler  调度器
     * @param jobId    定时任务id
     * @throws SchedulerException
     */
    public static void resumeScheduleJob(Scheduler scheduler, String jobId) {
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("启动定时任务出错："+e.getMessage());
        }
    }


    /**
     * 根据任务id立即运行一次定时任务
     * @param scheduler     调度器
     * @param jobId       定时任务id
     * @throws SchedulerException
     */
    public static void runOnce(Scheduler scheduler, String jobId){
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("运行定时任务出错："+e.getMessage());
        }
    }

    /**
     * 更新定时任务
     * @param scheduler   调度器
     * @param quartzBean  定时任务信息类
     * @throws SchedulerException
     */
    public static void updateScheduleJob(Scheduler scheduler, QuartzVo quartzBean)  {
        try {
            //获取到对应任务的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getId());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            //重新构建任务的触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据定时任务id从调度器当中删除定时任务
     * @param scheduler 调度器
     * @param jobId   定时任务名称
     *
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobId) {
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("删除定时任务出错："+e.getMessage());
        }
    }
}
