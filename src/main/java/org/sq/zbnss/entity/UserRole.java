package org.sq.zbnss.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户权限
 */
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = 2455220013366482894L;

    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

}