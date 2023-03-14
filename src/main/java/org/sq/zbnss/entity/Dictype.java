package org.sq.zbnss.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 字典类型(TbDictype)实体类
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
public class Dictype implements Serializable {
    private static final long serialVersionUID = -74263061143831748L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 字典id
     */
    private String typeId;
    /**
     * 类型标识
     */
    private String key;
    /**
     * 类型描述
     */
    private String value;

    @TableField(exist = false)
    private ArrayList<Dic> dics;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public ArrayList<Dic> getDics() {
        return dics;
    }

    public void setDics(ArrayList<Dic> dics) {
        this.dics = dics;
    }
}

