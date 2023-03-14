package org.sq.zbnss.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 */
@Data
public abstract class BaseVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;

    private Date createTime;
    private Date updateTime;

}