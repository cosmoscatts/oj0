package com.cc.oj0backend.github.model;

import lombok.Data;

@Data
public class GithubAccessToken {
    private String access_token;
    private String scope;
    private String token_type;
}