package com.cc.oj0backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.constant.CommonConstant;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.exception.ThrowUtils;
import com.cc.oj0backend.mapper.QuestionMapper;
import com.cc.oj0backend.model.dto.question.QuestionQueryRequest;
import com.cc.oj0backend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.cc.oj0backend.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.vo.QuestionSubmitVO;
import com.cc.oj0backend.model.vo.QuestionVO;
import com.cc.oj0backend.model.vo.UserVO;
import com.cc.oj0backend.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
//    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
//    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
//    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}




