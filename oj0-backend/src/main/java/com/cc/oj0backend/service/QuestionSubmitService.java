package com.cc.oj0backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.cc.oj0backend.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.vo.QuestionSubmitVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cosmoscatts
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-12-03 19:40:52
*/
@Service
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

    /**
     * 获取当前登录用户完成的所有题目 id
     *
     * @param loginUser
     * @return
     */
    List<Long> getMyAcceptedQuestionIdList(User loginUser);
}




