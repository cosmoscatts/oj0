package com.cc.oj0backend.judge.strategy;

import com.cc.oj0backend.judge.codesandbox.model.JudgeInfo;
import com.cc.oj0backend.model.dto.question.JudgeCase;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}

