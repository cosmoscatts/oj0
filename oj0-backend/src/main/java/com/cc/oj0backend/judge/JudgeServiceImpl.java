package com.cc.oj0backend.judge;

import cn.hutool.json.JSONUtil;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.judge.codesandbox.CodeSandbox;
import com.cc.oj0backend.judge.codesandbox.CodeSandboxFactory;
import com.cc.oj0backend.judge.codesandbox.CodeSandboxProxy;
import com.cc.oj0backend.judge.codesandbox.model.*;
import com.cc.oj0backend.judge.strategy.JudgeContext;
import com.cc.oj0backend.model.dto.question.JudgeCase;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.cc.oj0backend.model.enums.JudgeInfoMessageEnum;
import com.cc.oj0backend.model.enums.QuestionSubmitStatusEnum;
import com.cc.oj0backend.service.QuestionService;
import com.cc.oj0backend.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 更新提交数
        Question questionUpdate = new Question();
        questionUpdate.setId(question.getId());
        int submitNum = question.getSubmitNum() == null ? 0 : question.getSubmitNum();
        questionUpdate.setSubmitNum(submitNum + 1);
        update = questionService.updateById(questionUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交数更新错误");
        }
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        if (executeCodeResponse.getStatus().equals(3)) { // 沙箱内部错误
            questionSubmitUpdate = new QuestionSubmit();
            questionSubmitUpdate.setId(questionSubmitId);
            questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
            questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(executeCodeResponse.getJudgeInfo()));
            update = questionSubmitService.updateById(questionSubmitUpdate);
            if (!update) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
            }
            return questionSubmitService.getById(questionSubmitId);
        }
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 如果题目通过，修改题目通过数
        if (JudgeInfoMessageEnum.ACCEPTED.getValue().equals(judgeInfo.getMessage())) {
            questionUpdate = new Question();
            questionUpdate.setId(question.getId());
            int acceptedNum = question.getAcceptedNum() == null ? 0 : question.getAcceptedNum();
            questionUpdate.setAcceptedNum(acceptedNum + 1);
            update = questionService.updateById(questionUpdate);
            if (!update) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目通过数更新错误");
            }
        }
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    public TestCasesResponse doTestCases(TestCasesRequest testCasesRequest) {
        Long questionId = testCasesRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            return new TestCasesResponse().setStatus(QuestionSubmitStatusEnum.FAILED.getValue())
                    .setMessage("题目不存在");
        }

        String code = testCasesRequest.getCode();
        String language = testCasesRequest.getLanguage();
        List<String> inputList = testCasesRequest.getJudgeCaseList().stream()
                .map(JudgeCase::getInput).collect(Collectors.toList());

        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        if (executeCodeResponse.getStatus().equals(3)) { // 沙箱内部错误
            return new TestCasesResponse().setStatus(QuestionSubmitStatusEnum.FAILED.getValue())
                    .setJudgeInfo(executeCodeResponse.getJudgeInfo());
        }
        List<String> outputList = executeCodeResponse.getOutputList();
        // 根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(testCasesRequest.getJudgeCaseList());
        judgeContext.setQuestion(question);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改数据库中的判题结果
        return new TestCasesResponse().setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue())
                .setJudgeInfo(judgeInfo);
    }
}
