package com.cc.oj0backend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新密码请求体
 */
@Data
public class UserUpdateMyPasswordRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userOldPassword;

    private String userNewPassword;

    private String checkNewPassword;
}