package org.sq.zbnss.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.sq.zbnss.dao.StatisticsDao;
import org.sq.zbnss.entity.statistics.AppraisalStatistics;
import org.sq.zbnss.entity.statistics.CheckStatistics;
import org.sq.zbnss.entity.statistics.RecordStatistics;
import org.sq.zbnss.entity.statistics.SystemStatistics;
import org.sq.zbnss.service.StatisticsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.sq.zbnss.uitl.DateUtil.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsDao statisticsDao;

    @Override
    public JSONObject getStatisticsInfo() {
        JSONObject resultData = new JSONObject();
        SystemStatistics statistics = statisticsDao.querySysCount();
        ArrayList<AppraisalStatistics> appraisalStatistics = statisticsDao.queryAppraisalCount();
        ArrayList<RecordStatistics> recordStatistics = statisticsDao.queryRecordCount();
        ArrayList<CheckStatistics> checkStatistics = statisticsDao.queryCheckCount(null,null);
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        Date yearFirstDay = getCurrentFirstOfYear();
        ArrayList<CheckStatistics> checkStatistics1 = statisticsDao.queryCheckCount(yearFirstDay,null);
        ArrayList<CheckStatistics> checkStatistics2 = statisticsDao.queryCheckCount(getFirstOfYear(currentYear -1),getLastOfYear(currentYear -1));
        resultData.put("system", statistics);
        resultData.put("record", recordStatistics);
        resultData.put("appraisal", appraisalStatistics);
        resultData.put("check_all", checkStatistics);
        resultData.put("check_year", checkStatistics1);
        resultData.put("check_year_b", checkStatistics2);
        return resultData;
    }

    @Override
    public Object getDistributeInfo(String type, Date startTime, Date endTime){
        if("system".equals(type)){
            ArrayList<RecordStatistics>  sysList =  statisticsDao.queryComSys(startTime,endTime);
            return sysList;
        }else if("record".equals(type)){
            ArrayList<RecordStatistics>  reList =  statisticsDao.queryComRe(startTime,endTime);
            return reList;
        }else if("appraisal".equals(type)){
            ArrayList<AppraisalStatistics>  appList =  statisticsDao.queryComApp(startTime,endTime);
            return appList;
        }
        return null;
    }
}
