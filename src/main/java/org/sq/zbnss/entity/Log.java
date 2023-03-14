package org.sq.zbnss.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 操作日志(TbLog)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:22
 */
public class Log implements Serializable {
    private static final long serialVersionUID = -67780691955353323L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private User userId;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateDate;
    /**
     * 操作内容
     */
    private String operate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}

