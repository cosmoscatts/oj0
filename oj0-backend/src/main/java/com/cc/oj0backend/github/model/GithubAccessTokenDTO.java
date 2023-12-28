package com.cc.oj0backend.github.model;

import lombok.Data;

@Data
public class GithubAccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}