package org.sq.zbnss.entity.statistics;

import lombok.Data;

@Data
public class RecordStatistics {
    private int sysId;
    private String sysName;

    private int status;

    private int level;

    private int count;
}
