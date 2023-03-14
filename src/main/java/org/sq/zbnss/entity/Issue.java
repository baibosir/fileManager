package org.sq.zbnss.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 问题(TbIssue)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:21
 */
@Data
public class Issue implements Serializable {
    private static final long serialVersionUID = 814948802365072609L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位")
    private Company companyId;
    /**
     * 问题id
     */
    @ApiModelProperty(value = "问题id")
    private String issueId;
    /**
     * 问题数量
     */
    @ApiModelProperty(value = "问题数量")
    private int issueCount;
    /**
     * 问题类型
     */
    @ApiModelProperty(value = "问题类型")
    private Integer type;
    /**
     * 问题描述
     */
    @ApiModelProperty(value = "问题描述")
    private String description;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 系统id
     */
    @ApiModelProperty(value = "针对系统")
    private RecordSystem systemId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RecordSystem getSystemId() {
        return systemId;
    }

    public void setSystemId(RecordSystem systemId) {
        this.systemId = systemId;
    }
}

