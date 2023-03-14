package org.sq.zbnss.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 系统信息(TbSystem)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:27
 */
@Data
public class RecordSystem implements Serializable {
    private static final long serialVersionUID = 297567507150441436L;
    /**
     * 备案id
     */
    private Integer id;
    /**
     * 备案号
     */
    private String systemId;
    /**
     * 系统名称
     */
    private String name;
    /**
     * 单位id
     */
    private Company company;
    /**
     * 系统级别
     */
    private String level;
    /**
     * 上线时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date onlineDate;
    /**
     * 测评状态
     */
    private Integer testStatus;
    /**
     * 录入时间
     */
    @ApiModelProperty(value = "注册时间", hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "注册时间", hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Integer getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Integer testStatus) {
        this.testStatus = testStatus;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

