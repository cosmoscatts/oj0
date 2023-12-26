package com.cc.oj0backend.model.dto.questionsubmit;

import com.cc.oj0backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;


    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 创建时间最小值
     */
    private String createTimeMin;

    /**
     * 创建时间最小值
     */
    private String createTimeMax;

    private static final long serialVersionUID = 1L;
}

