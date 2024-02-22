package com.cc.oj0backend.judge.codesandbox.model;

import com.cc.oj0backend.model.dto.question.JudgeCase;
import lombok.Data;

import java.util.List;

@Data
public class TestCasesRequest {
    private List<JudgeCase> judgeCaseList;
    private String code;
    private String language;
    private Long questionId;
}
