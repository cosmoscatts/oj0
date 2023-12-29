package com.cc.oj0backend.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Github 登录配置
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "github")
@Data
public class GithubConfig {
    private String client_id;
    private String client_secret;
}
