package org.sq.zbnss.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 字典(TbDic)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
public class Dic implements Serializable {
    private static final long serialVersionUID = 402241160996699950L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 字典key
     */
    private String key;
    /**
     * 字典名称
     */
    private String value;
    /**
     * 字典描述
     */
    private String description;
    /**
     * 字典类型
     */
    private Dictype type;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Dictype getType() {
        return type;
    }

    public void setType(Dictype type) {
        this.type = type;
    }
}

