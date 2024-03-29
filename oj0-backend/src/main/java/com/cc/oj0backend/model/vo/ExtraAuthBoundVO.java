package com.cc.oj0backend.model.vo;

import lombok.Data;

/**
 * 用户第三方平台绑定信息
 */
@Data
public class ExtraAuthBoundVO {
    private boolean canUnbound; // 是否可以解绑
    private boolean github;
    private boolean gitee;
    private boolean qq;
    private boolean wechat;
}
