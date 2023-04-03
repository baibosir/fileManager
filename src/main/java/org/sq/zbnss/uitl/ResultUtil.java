package org.sq.zbnss.uitl;

import lombok.experimental.UtilityClass;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;

import java.util.List;

/**
 * 返回结果封装对象
 *

 */
@UtilityClass
public class ResultUtil {

    public static ResponseVo success() {
        return vo(CoreConst.SUCCESS_CODE, null, null);
    }

    public static ResponseVo success(String msg) {
        return vo(CoreConst.SUCCESS_CODE, msg, null);
    }

    public static ResponseVo success(String msg, Object data) {
        return vo(CoreConst.SUCCESS_CODE, msg, data);
    }

    public static ResponseVo error() {
        return vo(CoreConst.FAIL_CODE, null, null);
    }

    public static ResponseVo error(String msg) {
        return vo(CoreConst.FAIL_CODE, msg, null);
    }

    public static ResponseVo error(String msg, Object data) {
        return vo(CoreConst.FAIL_CODE, msg, data);
    }

    public static PageResultVo table(List<?> list, Long total) {
        return new PageResultVo(CoreConst.SUCCESS_CODE,list, total);
    }

    public static ResponseVo vo(Integer status, String message, Object data) {
        return new ResponseVo<>(status, message, data);
    }


}
