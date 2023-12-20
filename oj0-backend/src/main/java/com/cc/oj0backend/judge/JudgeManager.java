package com.cc.oj0backend.judge;

import com.cc.oj0backend.judge.codesandbox.model.JudgeInfo;
import com.cc.oj0backend.judge.strategy.DefaultJudgeStrategy;
import com.cc.oj0backend.judge.strategy.JavaLanguageJudgeStrategy;
import com.cc.oj0backend.judge.strategy.JudgeContext;
import com.cc.oj0backend.judge.strategy.JudgeStrategy;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
