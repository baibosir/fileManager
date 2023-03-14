package org.sq.zbnss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -8736616045315083846L;

    @ApiModelProperty(value = "用户id")
    private int id;

    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 加密盐值
     */
    @ApiModelProperty( hidden= true)
    private String salt;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "test")
    private String nickname;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 性别：1男2女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 头像
     */
    private String img;

    /**
     * 用户状态：1有效; 0无效
     */
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty( hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty( hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 最后登录时间
     */
    @ApiModelProperty( hidden= true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 登录ip
     */
    @ApiModelProperty( hidden= true)
    @TableField(exist = false)
    private String loginIpAddress;

    /**
     * 角色
     */
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 重写获取盐值方法，自定义realm使用
     * Gets credentials salt.
     *
     * @return the credentials salt
     */
    public String getCredentialsSalt() {
        return username + "" + salt;
    }


}