package com.cc.oj0codesandbox;

import com.cc.oj0codesandbox.model.ExecuteCodeRequest;
import com.cc.oj0codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

