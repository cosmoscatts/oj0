package com.cc.oj0backend.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 做题分析数据总结
 */
@Data
@Accessors(chain = true)
public class ResolveAnalysisSummaryVO implements Serializable {
    /**
     * 已通过题目
     */
    private Long acceptedQuestionNum;

    /**
     * 提交未通过题目
     */
    private Long unacceptedQuestionNum;

    /**
     * 未开始题目
     */
    private Long unStartedQuestionNum;

    /**
     * 提交总数
     */
    private Long submitTotalNum;

    /**
     * 通过的提交
     */
    private Long acceptedSubmitNum;

    /**
     * 提交通过率
     */
    private Double submitAcceptPercent;

    private static final long serialVersionUID = 1L;
}
