package org.sq.zbnss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 单位信息(TbCompany)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:19
 */
@Data
public class Company implements Serializable {
    private static final long serialVersionUID = 546174417406015956L;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id(系统自动生成)")
    private Integer id;
    /**
     * 单位编号
     */
    @ApiModelProperty(value = "单位编号(系统自动生成)")
    @TableId(type = IdType.ASSIGN_UUID)
    private String companyId;

    @ApiModelProperty(value = "单位名称")
    private String name;
    /**
     * 单位负责人
     */
    @ApiModelProperty(value = "负责人")
    private User principal;
    /**
     * 单位地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 单位电话
     */
    @ApiModelProperty(value = "电弧")
    private String telephone;
    /**
     * 单位描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 单位类型
     */
    @ApiModelProperty(value = "单位类型")
    private Integer type;
    /**
     * 一级系统数
     */
    @ApiModelProperty(value = "一级系统数量")
    private Integer numSystem1;
    /**
     * 二级系统数
     */
    @ApiModelProperty(value = "二系统数量")
    private Integer numSystem2;
    /**
     * 三级系统数
     */
    @ApiModelProperty(value = "三级系统数量")
    private Integer numSystem3;
    /**
     * 录入时间
     */
    @ApiModelProperty(value = "注册时间", hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;
    /**
     * 更新时间
     */
    @ApiModelProperty( hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 录入人
     */
    @ApiModelProperty( hidden= true)
    private User insertUser;
    /**
     * 更新人
     */
    @ApiModelProperty( hidden= true)
    private User updateUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumSystem1() {
        return numSystem1;
    }

    public void setNumSystem1(Integer numSystem1) {
        this.numSystem1 = numSystem1;
    }

    public Integer getNumSystem2() {
        return numSystem2;
    }

    public void setNumSystem2(Integer numSystem2) {
        this.numSystem2 = numSystem2;
    }

    public Integer getNumSystem3() {
        return numSystem3;
    }

    public void setNumSystem3(Integer numSystem3) {
        this.numSystem3 = numSystem3;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

