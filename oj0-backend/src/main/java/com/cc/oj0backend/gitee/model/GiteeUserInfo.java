package com.cc.oj0backend.gitee.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GiteeUserInfo {
    private String id;
    private String login;
    private String name;
    private String avatar_url;
    private String remark;
}
