package org.sq.zbnss.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 测评信息(TbAppraisal)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:11
 */
public class Appraisal implements Serializable {
    private static final long serialVersionUID = -18014066116947518L;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 测评id
     */
    @ApiModelProperty(value = "测评id")
    private String appraisal;
    /**
     * 系统id
     */
    @ApiModelProperty(value = "针对系统id")
    private Integer systemId;
    /**
     * 测评状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 测评次数
     */
    @ApiModelProperty(value = "测评编号")
    private Integer num;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private User insertUser;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private User updateUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public User getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(User insertUser) {
        this.insertUser = insertUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }
}

