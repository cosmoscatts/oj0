package com.cc.oj0backend.model.dto.resloveanalysis;

import com.cc.oj0backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResolveAnalysisQueryRequest extends PageRequest implements Serializable {

    /**
     * 题目状态（0: 已通过题目；1：提交未通过；2：未开始题目）
     */
    private Integer selectedStatus;

    /**
     * 搜索内容（题目 id）
     */
    private String searchContent;

    private static final long serialVersionUID = 1L;
}
