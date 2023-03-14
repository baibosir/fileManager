package org.sq.zbnss.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 培训记录(TbTrain)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:28
 */
public class Train implements Serializable {
    private static final long serialVersionUID = -69929860370951155L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "培训id（系统自动生成）")
    private Integer id;
    /**
     * 培训id
     */
    @ApiModelProperty(value = "培训编号（系统自动生成用于存放文件）")
    private String trainId;
    /**
     * 培训单位
     */
    @ApiModelProperty(value = "受配置的单位")
    private Company companyId;
    /**
     * 培训类型
     */
    @ApiModelProperty(value = "培训类型")
    private Integer type;
    /**
     * 培训方
     */
    @ApiModelProperty(value = "培训人单位")
    private String trainCompany;
    /**
     * 讲师
     */
    @ApiModelProperty(value = "培训讲师")
    private String lacture;
    /**
     * 计划开始时间
     */
    @ApiModelProperty(value = "计划培训开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStime;
    /**
     * 计划完成时间
     */
    @ApiModelProperty(value = "计划培训完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEtime;
    /**
     * 实际开始时间
     */
    @ApiModelProperty(value = "实际开始培训时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTiem;
    /**
     * 实际完成时间
     */
    @ApiModelProperty(value = "实际完成培训时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 培训状态
     */
    @ApiModelProperty(value = "培训状态" )
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "记录创建时间" ,hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "记录更新时间",hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTrainCompany() {
        return trainCompany;
    }

    public void setTrainCompany(String trainCompany) {
        this.trainCompany = trainCompany;
    }

    public String getLacture() {
        return lacture;
    }

    public void setLacture(String lacture) {
        this.lacture = lacture;
    }

    public Date getPlanStime() {
        return planStime;
    }

    public void setPlanStime(Date planStime) {
        this.planStime = planStime;
    }

    public Date getPlanEtime() {
        return planEtime;
    }

    public void setPlanEtime(Date planEtime) {
        this.planEtime = planEtime;
    }

    public Date getStartTiem() {
        return startTiem;
    }

    public void setStartTiem(Date startTiem) {
        this.startTiem = startTiem;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
}

