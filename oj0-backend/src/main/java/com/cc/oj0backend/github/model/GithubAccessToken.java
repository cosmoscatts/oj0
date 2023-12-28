package com.cc.oj0backend.github.model;

import lombok.Data;

@Data
public class GithubAccessToken {
    private String accessToken;
    private String scope;
    private String tokenType;
}