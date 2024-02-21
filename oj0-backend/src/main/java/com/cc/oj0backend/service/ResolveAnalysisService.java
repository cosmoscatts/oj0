package com.cc.oj0backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.model.dto.resloveanalysis.ResolveAnalysisQueryRequest;
import com.cc.oj0backend.model.vo.ResolveAnalysisItemVO;

public interface ResolveAnalysisService {
    Page<ResolveAnalysisItemVO> getSubmitList(Long userId, ResolveAnalysisQueryRequest analysisQueryRequest);
}
