package com.cc.oj0backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.model.dto.resloveanalysis.ResolveAnalysisQueryRequest;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.cc.oj0backend.model.vo.ResolveAnalysisItemVO;
import com.cc.oj0backend.service.QuestionService;
import com.cc.oj0backend.service.QuestionSubmitService;
import com.cc.oj0backend.service.ResolveAnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResolveAnalysisServiceImpl implements ResolveAnalysisService {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    public Page<ResolveAnalysisItemVO> getSubmitList(Long userId, ResolveAnalysisQueryRequest analysisQueryRequest) {
        int searchStatus = analysisQueryRequest.getSelectedStatus();
        String searchContent = analysisQueryRequest.getSearchContent();
        long current = analysisQueryRequest.getCurrent();
        long size = analysisQueryRequest.getPageSize();

        QueryWrapper<QuestionSubmit> questionSubmitQueryWrapper = new QueryWrapper<QuestionSubmit>()
                .eq("userId", userId);
        if (searchContent != null) {
            questionSubmitQueryWrapper.like("questionId", searchContent);
        }
        // 查找未开始题目
        if (searchStatus == 2) {
            // 找到用户的所有提交记录
            List<QuestionSubmit> submitList = questionSubmitService.list(questionSubmitQueryWrapper);
            Set<Long> hasSubmittedIds = submitList.stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
            Page<Question> page = questionService.page(new Page<>(current, size), Wrappers.<Question>lambdaQuery().notIn(Question::getId, hasSubmittedIds));
            return fromQuestionPage(page);
        }
        if (searchStatus == 0) { // 已经通过
            questionSubmitQueryWrapper.eq("status", 2)
                    .like("judgeInfo", "Accepted");
        } else if (searchStatus == 1) { // 提交未通过
            questionSubmitQueryWrapper.ne("status", 2)
                    .or().notLike("judgeInfo", "Accepted");
        }
        questionSubmitQueryWrapper.select("questionId", "count(questionId) count", "max(createTime) lastSubmitTime").groupBy("questionId");
        Page<Map<String, Object>> page = questionSubmitService.pageMaps(new Page<>(current, size), questionSubmitQueryWrapper);
        return fromQuestionSubmitPage(page);
    }

    private Page<ResolveAnalysisItemVO> fromQuestionPage(Page<Question> page) {
        return new Page<ResolveAnalysisItemVO>()
                .setRecords(page.getRecords().stream().map(i -> new ResolveAnalysisItemVO()
                        .setQuestionId(i.getId()).setTitle(i.getTitle()).setDifficulty(i.getDifficulty()))
                        .collect(Collectors.toList())
                ).setTotal(page.getTotal());
    }

    private Page<ResolveAnalysisItemVO> fromQuestionSubmitPage(Page<Map<String, Object>> page) {
        List<Question> list = questionService.list();
        Map<Long, Question> map = new HashMap<>();
        for (Question question : list) {
            map.put(question.getId(), question);
        }
        return new Page<ResolveAnalysisItemVO>()
                .setRecords(page.getRecords().stream().map(i -> {
                    Long questionId = (Long) i.get("questionId");
                    Question question = map.getOrDefault(questionId, new Question());
                    LocalDateTime localDateTime = (LocalDateTime) i.get("lastSubmitTime");
                    Date lastSubmitTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    return new ResolveAnalysisItemVO()
                                    .setQuestionId(questionId)
                                    .setTitle(question.getTitle())
                                    .setDifficulty(question.getDifficulty())
                                    .setCount((Long) i.get("count"))
                                    .setLastSubmitTime(lastSubmitTime);
                                }).collect(Collectors.toList())
                ).setTotal(page.getTotal());
    }
}
