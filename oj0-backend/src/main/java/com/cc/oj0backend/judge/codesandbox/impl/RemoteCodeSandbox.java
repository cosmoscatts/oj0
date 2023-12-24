package com.cc.oj0backend.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.judge.codesandbox.CodeSandbox;
import com.cc.oj0backend.judge.codesandbox.model.ExecuteCodeRequest;
import com.cc.oj0backend.judge.codesandbox.model.ExecuteCodeResponse;
import com.cc.oj0backend.common.ErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "xxx";

    private static final String AUTH_REQUEST_SECRET = "xxx";

    private static final String CODE_SANDBOX_URL = "http://localhost:8090/executeCode";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(CODE_SANDBOX_URL)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
