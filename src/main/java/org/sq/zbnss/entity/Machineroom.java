package org.sq.zbnss.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 机房(TbMachineroom)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:24
 */
public class Machineroom implements Serializable {
    private static final long serialVersionUID = -43885093628759859L;
    /**
     * id
     */
    private Integer id;
    /**
     * 机房ID
     */
    private String machineId;
    /**
     * 机房位置
     */
    @ApiModelProperty(value = "机房位置" , required = true, example = "**单位**号楼**层**号房间")
    private String address;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "所属单位")
    private Company companyId;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private User principal;
    /**
     * 登记电话
     */
    @ApiModelProperty(value = "管理员或机房电话")
    private String regTel;
    /**
     * 登记时间
     */
    @ApiModelProperty(value = "机房启用状态")
    private Date regDate;
    /**
     * 机房状态
     */
    @ApiModelProperty(value = "机房状态")
    private String status;
    /**
     * 机房详情
     */
    @ApiModelProperty(value = "机房描述，例如机房的设备数量，占地面积以及设备类型等信息")
    private String detail;
    /**
     * 用途
     */
    @ApiModelProperty(value = "机房的用途")
    private String use;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegTel() {
        return regTel;
    }

    public void setRegTel(String regTel) {
        this.regTel = regTel;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
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

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }
}

