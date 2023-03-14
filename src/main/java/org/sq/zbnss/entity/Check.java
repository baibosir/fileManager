package org.sq.zbnss.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 检查结果记录(TbCheck)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:15
 */
@Data
public class Check implements Serializable {
    private static final long serialVersionUID = 804960663030254425L;
    /**
     * id
     */
    @ApiModelProperty(value = "检查id(系统自动生成)")
    private Integer id;
    /**
     * 检查id
     */
    @ApiModelProperty(value = "编号(系统自动生成，勇于存放文件)")
    private String checkId;
    /**
     * 检查人id
     */
    @ApiModelProperty(value = "检查人")
    private User checkUser;
    /**
     * 计划检查时间
     */
    @ApiModelProperty(value = "计划开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planDate;
    /**
     * 实际检查时间
     */
    @ApiModelProperty(value = "实际开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;
    /**
     * 检查内容
     */
    @ApiModelProperty(value = "内容")
    private String detail;
    /**
     * 检查状态
     */
    @ApiModelProperty(value = "检查状态")
    private Integer status;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "被检查单位")
    private Company companyId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date instertTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private User updateUser;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private User insertUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInstertTime() {
        return instertTime;
    }

    public void setInstertTime(Date instertTime) {
        this.instertTime = instertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public User getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(User insertUser) {
        this.insertUser = insertUser;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
}

