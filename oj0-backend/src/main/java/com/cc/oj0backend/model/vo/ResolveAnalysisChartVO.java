package com.cc.oj0backend.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 做题分析图标数据
 */
@Data
@Accessors(chain = true)
public class ResolveAnalysisChartVO implements Serializable {
    /**
     * 已通过题目
     */
    private List<Long> accepted;

    /**
     * 提交未通过题目
     */
    private List<Long> unaccepted;

    /**
     * 未开始题目
     */
    private List<Long> unStarted;

    private static final long serialVersionUID = 1L;
}
