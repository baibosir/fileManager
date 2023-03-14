package org.sq.zbnss.base;

import lombok.Data;

/**
 */
@Data
public class ChangePasswordVo {

    String oldPassword;
    String newPassword;
    String confirmNewPassword;

}
