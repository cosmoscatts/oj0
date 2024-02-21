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
import com.cc.oj0backend.model.vo.ResolveAnalysisChartVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisItemVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisSummaryVO;
import com.cc.oj0backend.service.QuestionSubmitService;
import com.cc.oj0backend.service.ResolveAnalysisService;
import com.cc.oj0backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        return ResultUtils.success(resolveAnalysisService.getDetailList(loginUser, questionId, status));
    }

    @GetMapping("/summary")
    public BaseResponse<ResolveAnalysisSummaryVO> getSummaryData(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(resolveAnalysisService.getSummaryData(loginUser.getId()));
    }

    @GetMapping("/chart")
    public BaseResponse<ResolveAnalysisChartVO> getChartData(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(resolveAnalysisService.getChartData(loginUser.getId()));
    }

}
