package org.sq.zbnss.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口响应状态枚举类
 *
 */
@Getter
@AllArgsConstructor
public enum FileType {

    TYPE_CE_PING("CEPING", "测评文件"),

    TYPE_BEI_AN("BEIAN", "备案"),

    TYPE_PEI_XUN("PEIXUN", "培训"),

    TYPE_JI_FANG("JIFANG", "机房文件"),

    TYPE_DAN_WEI("DANWEI", "单位");


    private final String value;
    private final String describe;

}