package com.cc.oj0codesandbox.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 判题信息
 */
@Data
@Accessors(chain = true)
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间（KB）
     */
    private Long time;
}
