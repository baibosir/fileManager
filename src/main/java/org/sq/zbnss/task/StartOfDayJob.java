package org.sq.zbnss.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.sq.zbnss.entity.*;
import org.sq.zbnss.service.*;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class StartOfDayJob extends QuartzJobBean {

    @Resource
    private UserMessageService userMessageService;

    @Resource
    private SystemService systemService;

    @Resource
    private AppraisalService appraisalService;

    @Resource
    private CheckService checkService;

    @Value("${application.record-times}")
    private int recordTimes ;

    @Value("${application.appraisal-times}")
    private int appraisalTimes ;

    @Value("${application.check-times}")
    private int checkTimes ;

    @Value("${application.next-appraisal-interval}")
    private int nextAppraisalTimes ;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        System.out.println(checkTimes);
        //获取所有备案信息
        Date now = new Date();
        ArrayList<RecordSystem> systems = systemService.queryByPage(new RecordSystem());
        for(RecordSystem item : systems){
            ArrayList<Appraisal>  appraisals = item.getAppraisalList();

            if(appraisals.size()  == 0 && !item.getLevel().equals(1)){
                Date sysRegDate = item.getRegistTime();

                long days=(now.getTime()-sysRegDate.getTime())/(1000*3600*24);
                if(days > recordTimes - 7){
                    UserMessage message = new UserMessage();
                    message.setStatus(1);
                    message.setMessages(MessageFormat.format("{3}单位{0}(备案id为：{2})已经备案{1}天,请尽快开始安排测评",
                            item.getName(),days,item.getId(),item.getCompany().getName()));
                    message.setCreated(now);
                    int i = userMessageService.register(message);
                    System.out.println(i);
                }
            }else{
                for(Appraisal appraisal : appraisals){
                    if(appraisal.getStatus().getId() != 10 && appraisal.getStatus().getId() != 11){
                       Date  appUpdateTime =  appraisal.getUpdateTime();
                        long days1=(now.getTime()-appUpdateTime.getTime())/(1000*3600*24);
                        if(days1 > appraisalTimes){
                            UserMessage message = new UserMessage();
                            message.setStatus(1);
                            message.setMessages(MessageFormat.format("{3}单位{0}(备案id为：{2})的测评计划以已开始{1},请尽快完成测评",
                                    item.getName(),days1,item.getId(),item.getCompany().getName()));
                            message.setCreated(now);
                            int i = userMessageService.register(message);
                            System.out.println(i);
                        }
                    }
                }
                Appraisal lastAppraisal = appraisals.get(0);
                if(lastAppraisal.getStatus().getId() != 10 || lastAppraisal.getStatus().getId() != 11){
                    Date  appUpdateTime =  lastAppraisal.getUpdateTime();
                    long days1=(now.getTime()-appUpdateTime.getTime())/(1000*3600*24);
                    if(days1 > nextAppraisalTimes){
                        UserMessage message = new UserMessage();
                        message.setStatus(1);
                        message.setMessages(MessageFormat.format("{3}单位{0}(备案id为：{2})的距离上次测评已过{1}天,请尽快安排复测评",
                                item.getName(),days1,item.getId(),item.getCompany().getName()));
                        message.setCreated(now);
                        int i = userMessageService.register(message);
                        System.out.println(i);
                    }
                }
            }
        }

        ArrayList<Check> checks = checkService.queryByPage(new Check());

        for(Check check : checks){
            Dic dic = check.getStatus();
            if(dic.getId() != 15 && dic.getId() != 16){
                Date planStartTime = check.getPlanDate();
                Date startDate = check.getCheckDate();
                if(startDate == null){
                    long days=(now.getTime()-planStartTime.getTime())/(1000*3600*24);
                    if(days > checkTimes){
                        UserMessage message = new UserMessage();
                        message.setStatus(1);
                        message.setMessages(MessageFormat.format("{0}单位的检查(id为：{1})按计划已经开始检查{2}天,但实际尚未开始,请尽快安排开始检查",
                                check.getCompanyId().getName(),check.getCheckId(),days));
                        message.setCreated(now);
                        int i = userMessageService.register(message);
                        System.out.println(i);
                    }
                }else{
                    long days=(now.getTime()-startDate.getTime())/(1000*3600*24);
                    if(days > checkTimes){
                        UserMessage message = new UserMessage();
                        message.setStatus(1);
                        message.setMessages(MessageFormat.format("{0}单位的检查(id为：{1})已经开始检查{1}天,请尽快完成检查",
                                check.getCompanyId().getName(),check.getCheckId(),days));
                        message.setCreated(now);
                        int i = userMessageService.register(message);
                        System.out.println(i);
                    }
                }
            }
        }

    }
}
