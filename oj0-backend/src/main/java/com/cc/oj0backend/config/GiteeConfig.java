package com.cc.oj0backend.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Gitee 登录配置
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "gitee")
@Data
public class GiteeConfig {
    private String client_id;
    private String client_secret;
}
