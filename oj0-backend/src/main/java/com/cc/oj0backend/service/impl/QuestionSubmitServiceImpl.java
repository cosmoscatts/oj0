package com.cc.oj0backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.enums.FileUploadBizEnum;
import com.cc.oj0backend.service.QuestionService;
import com.cc.oj0backend.service.QuestionSubmitService;
import com.cc.oj0backend.mapper.QuestionSubmitMapper;
import com.cc.oj0backend.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author cosmoscatts
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-12-03 19:40:52
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {


    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
//        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
//        if (languageEnum == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
//        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        // 设置初始状态
//        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
//        // 执行判题服务
//        CompletableFuture.runAsync(() -> {
//            judgeService.doJudge(questionSubmitId);
//        });
        return questionSubmitId;
    }
}




