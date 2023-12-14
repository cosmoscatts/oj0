package com.cc.oj0backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云对象存储客户端
 */
@Configuration
@ConfigurationProperties(prefix = "oss.qiniu")
@Data
public class QiNiuClientConfig {
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 七牛域名 domain
     */
    private String url;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 目录名称
     */
    private String dir;
}
