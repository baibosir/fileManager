package org.sq.zbnss.dao;

import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.statistics.AppraisalStatistics;
import org.sq.zbnss.entity.statistics.CheckStatistics;
import org.sq.zbnss.entity.statistics.RecordStatistics;
import org.sq.zbnss.entity.statistics.SystemStatistics;

import java.util.ArrayList;
import java.util.Date;

@Mapper
public interface StatisticsDao {
    SystemStatistics querySysCount();

    ArrayList<AppraisalStatistics> queryAppraisalCount();

    ArrayList<RecordStatistics> queryRecordCount();

    ArrayList<CheckStatistics> queryCheckCount(Date startTime, Date endTime);

    ArrayList<RecordStatistics> queryComSys(Date startTime, Date endTime);

    ArrayList<RecordStatistics> queryComRe(Date startTime, Date endTime);

    ArrayList<AppraisalStatistics> queryComApp(Date startTime, Date endTime);
}
