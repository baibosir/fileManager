package org.sq.zbnss.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QuartzVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    private String id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务状态 启动还是暂停
     */
    private Integer status;

    /**
     * 任务运行时间表达式
     */
    private String cronExpression;
}
