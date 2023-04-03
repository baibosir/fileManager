package org.sq.zbnss.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface StatisticsService {

    public JSONObject getStatisticsInfo();

    Object getDistributeInfo(String type, Date startTime, Date endTime);
}
