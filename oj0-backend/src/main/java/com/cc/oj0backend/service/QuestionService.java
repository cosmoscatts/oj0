package com.cc.oj0backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.model.dto.post.PostQueryRequest;
import com.cc.oj0backend.model.dto.question.QuestionQueryRequest;
import com.cc.oj0backend.model.entity.Post;
import com.cc.oj0backend.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.oj0backend.model.vo.PostVO;
import com.cc.oj0backend.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author cosmoscatts
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-12-03 19:38:53
*/
public interface QuestionService extends IService<Question> {

    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
