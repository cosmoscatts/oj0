package com.cc.oj0backend.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 做题分析
 */
@Data
@Accessors(chain = true)
public class ResolveAnalysisItemVO implements Serializable {
    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目难度
     */
    private String difficulty;

    /**
     * 提交次数
     */
    private Long count;

    /**
     * 最近提交时间
     */
    private Date lastSubmitTime;

    private static final long serialVersionUID = 1L;
}
