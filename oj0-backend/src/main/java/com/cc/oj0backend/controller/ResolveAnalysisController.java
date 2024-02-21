package com.cc.oj0backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.common.BaseResponse;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.common.ResultUtils;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.model.dto.resloveanalysis.ResolveAnalysisDetailQueryRequest;
import com.cc.oj0backend.model.dto.resloveanalysis.ResolveAnalysisQueryRequest;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.vo.QuestionSubmitVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisItemVO;
import com.cc.oj0backend.service.QuestionSubmitService;
import com.cc.oj0backend.service.ResolveAnalysisService;
import com.cc.oj0backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 做题分析页面接口
 */
@RestController
@RequestMapping("/resolve-analysis")
@Slf4j
public class ResolveAnalysisController {

    @Resource
    private UserService userService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private ResolveAnalysisService resolveAnalysisService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<ResolveAnalysisItemVO>> listSubmitInfo(@RequestBody ResolveAnalysisQueryRequest analysisQueryRequest, HttpServletRequest request) {
        if (analysisQueryRequest == null || analysisQueryRequest.getSelectedStatus() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(resolveAnalysisService.getSubmitList(loginUser.getId(), analysisQueryRequest));
    }

    /**
     * 查询提交记录（区分【通过】、【提交未通过】）
     */
    @PostMapping("/list/detail/vo")
    public BaseResponse<List<QuestionSubmitVO>> listDetail(@RequestBody ResolveAnalysisDetailQueryRequest detailQueryRequest,
                                                           HttpServletRequest request) {
        if (detailQueryRequest == null || detailQueryRequest.getStatus() == null
                || detailQueryRequest.getQuestionId() == null || detailQueryRequest.getQuestionId() == 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int status = detailQueryRequest.getStatus();
        long questionId = detailQueryRequest.getQuestionId();
        User loginUser = userService.getLoginUser(request);
        LambdaQueryWrapper<QuestionSubmit> queryWrapper = Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getQuestionId, questionId)
                .eq(QuestionSubmit::getUserId, loginUser.getId()).orderByDesc(QuestionSubmit::getCreateTime);
        if (status == 0) { // 已经通过
            queryWrapper.eq(QuestionSubmit::getStatus, 2).like(QuestionSubmit::getJudgeInfo, "Accepted");
        } else if (status == 1) { // 提交未通过
            queryWrapper.ne(QuestionSubmit::getStatus, 2).or().notLike(QuestionSubmit::getJudgeInfo, "Accepted");
        }
        List<QuestionSubmit> submitList = questionSubmitService.list(queryWrapper);
        List<QuestionSubmitVO> result = submitList.stream()
                .map(questionSubmit -> questionSubmitService.getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        return ResultUtils.success(result);
    }
}
