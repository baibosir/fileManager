package org.sq.zbnss.entity.statistics;

import lombok.Data;

@Data
public class SystemStatistics {
    private int sysId;
    private String sysName;
    //系统总数
    private int allCount;
    //一级系统数
    private int sys1Count;
    //二级系统数
    private int sys2Count;
    //三级系统数
    private int sys3Count;
}
