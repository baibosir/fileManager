package org.sq.zbnss.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 */
@Data
@AllArgsConstructor
public class PageResultVo {
    private Integer status;
    private List rows;
    private Long total;

}
