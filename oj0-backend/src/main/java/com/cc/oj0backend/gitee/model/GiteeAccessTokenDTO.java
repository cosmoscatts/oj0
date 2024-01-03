package com.cc.oj0backend.gitee.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GiteeAccessTokenDTO {
    private String grant_type = "authorization_code";
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri = "https://oj.hilyc.cn/check-auth/gitee/";
}
