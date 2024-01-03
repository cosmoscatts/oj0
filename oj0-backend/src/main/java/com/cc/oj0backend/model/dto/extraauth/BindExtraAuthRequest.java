package com.cc.oj0backend.model.dto.extraauth;

import lombok.Data;

@Data
public class BindExtraAuthRequest {
    private String code;
    private String redirectUri;
}
