package com.cc.oj0backend.judge;

import com.cc.oj0backend.judge.codesandbox.model.TestCasesRequest;
import com.cc.oj0backend.judge.codesandbox.model.TestCasesResponse;
import com.cc.oj0backend.model.entity.QuestionSubmit;

public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

    /**
     * 测试运行
     */
    TestCasesResponse doTestCases(TestCasesRequest testCasesRequest);
}
