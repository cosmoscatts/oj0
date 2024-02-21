package com.cc.oj0backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.model.dto.resloveanalysis.ResolveAnalysisQueryRequest;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.vo.QuestionSubmitVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisChartVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisItemVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisSummaryVO;

import java.util.List;

public interface ResolveAnalysisService {
    Page<ResolveAnalysisItemVO> getSubmitList(Long userId, ResolveAnalysisQueryRequest analysisQueryRequest);

    List<QuestionSubmitVO> getDetailList(User loginUser, Long questionId, Integer status);

    ResolveAnalysisSummaryVO getSummaryData(Long userId);

    ResolveAnalysisChartVO getChartData(Long userId);
}
