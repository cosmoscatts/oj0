package com.cc.oj0backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.oj0backend.model.dto.resloveanalysis.ResolveAnalysisQueryRequest;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.model.entity.QuestionSubmit;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.vo.QuestionSubmitVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisChartVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisItemVO;
import com.cc.oj0backend.model.vo.ResolveAnalysisSummaryVO;
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
            questionSubmitQueryWrapper.and(qw -> qw.ne("status", 2).or()
                    .notLike("judgeInfo", "Accepted"));
        }
        questionSubmitQueryWrapper.select("questionId", "count(questionId) count", "max(createTime) lastSubmitTime")
                .groupBy("questionId").orderByDesc("lastSubmitTime");
        Page<Map<String, Object>> page = questionSubmitService.pageMaps(new Page<>(current, size), questionSubmitQueryWrapper);
        return fromQuestionSubmitPage(page);
    }

    @Override
    public List<QuestionSubmitVO> getDetailList(User loginUser, Long questionId, Integer status) {
        LambdaQueryWrapper<QuestionSubmit> queryWrapper = Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getQuestionId, questionId)
                .eq(QuestionSubmit::getUserId, loginUser.getId()).orderByDesc(QuestionSubmit::getCreateTime);
        if (status == 0) { // 已经通过
            queryWrapper.eq(QuestionSubmit::getStatus, 2).like(QuestionSubmit::getJudgeInfo, "Accepted");
        } else if (status == 1) { // 提交未通过
            queryWrapper.and(qw -> qw.ne(QuestionSubmit::getStatus, 2).or()
                    .notLike(QuestionSubmit::getJudgeInfo, "Accepted"));
        }
        List<QuestionSubmit> submitList = questionSubmitService.list(queryWrapper);
        List<QuestionSubmitVO> result = submitList.stream()
                .map(questionSubmit -> questionSubmitService.getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public ResolveAnalysisSummaryVO getSummaryData(Long userId) {
        // 已通过题目
        List<Long> acceptedQuestionIds = questionSubmitService.listMaps(
                        Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId)
                                .eq(QuestionSubmit::getStatus, 2)
                                .like(QuestionSubmit::getJudgeInfo, "Accepted")
                                .select(QuestionSubmit::getQuestionId)
                                .groupBy(QuestionSubmit::getQuestionId))
                .stream().map(i -> (Long)i.get("questionId")).collect(Collectors.toList());
        long acceptedQuestionNum = acceptedQuestionIds.size();
        // 提交未通过的题目
        long unacceptedQuestionNum = questionSubmitService.listMaps(
                Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId)
                        .notIn(QuestionSubmit::getQuestionId, acceptedQuestionIds)
                        .and(qw -> qw.ne(QuestionSubmit::getStatus, 2).or()
                                .notLike(QuestionSubmit::getJudgeInfo, "Accepted"))
                        .select(QuestionSubmit::getQuestionId)
                        .groupBy(QuestionSubmit::getQuestionId)).size();
        // 未开始题目
        Set<Long> questionIds = questionSubmitService.list(Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId))
                .stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
        long unStartedQuestionNum = questionService.count(Wrappers.<Question>lambdaQuery().notIn(Question::getId, questionIds));
        // 提交总数
        long submitTotalNum = questionSubmitService.count(
                Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId));
        // 通过的提交
        long acceptedSubmitNum = questionSubmitService.count(
                Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId)
                        .eq(QuestionSubmit::getStatus, 2)
                        .like(QuestionSubmit::getJudgeInfo, "Accepted"));
        // 提交通过率
        double submitAcceptPercent = submitTotalNum > 0
                ? (double) acceptedSubmitNum / submitTotalNum
                : 0d;
        ResolveAnalysisSummaryVO result = new ResolveAnalysisSummaryVO().setAcceptedQuestionNum(acceptedQuestionNum)
                .setUnacceptedQuestionNum(unacceptedQuestionNum).setUnStartedQuestionNum(unStartedQuestionNum)
                .setSubmitTotalNum(submitTotalNum).setAcceptedSubmitNum(acceptedSubmitNum)
                .setSubmitAcceptPercent(submitAcceptPercent);
        return result;
    }

    @Override
    public ResolveAnalysisChartVO getChartData(Long userId) {
        List<Long> accepted, unaccepted, unStarted;
        // 所有已经提交过的题目 id
        Set<Long> submittedIds = questionSubmitService.list(Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId))
                .stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
        // 已通过题目 id
        List<Long> acceptedIds = questionSubmitService.listMaps(
                        Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId)
                                .eq(QuestionSubmit::getStatus, 2)
                                .like(QuestionSubmit::getJudgeInfo, "Accepted")
                                .select(QuestionSubmit::getQuestionId)
                                .groupBy(QuestionSubmit::getQuestionId))
                .stream().map(i -> (Long)i.get("questionId")).collect(Collectors.toList());
        List<Question> acceptedQuestions = questionService.list(Wrappers.<Question>lambdaQuery().in(Question::getId, acceptedIds));
        accepted = count(acceptedQuestions);
        // 提交未通过题目 id
        List<Long> unacceptedIds = questionSubmitService.listMaps(
                Wrappers.<QuestionSubmit>lambdaQuery().eq(QuestionSubmit::getUserId, userId)
                        .notIn(QuestionSubmit::getQuestionId, acceptedIds)
                        .and(qw -> qw.ne(QuestionSubmit::getStatus, 2).or()
                                .notLike(QuestionSubmit::getJudgeInfo, "Accepted"))
                        .select(QuestionSubmit::getQuestionId)
                        .groupBy(QuestionSubmit::getQuestionId)
        ).stream().map(i -> (Long) i.get("questionId")).collect(Collectors.toList());
        List<Question> unacceptedQuestions = questionService.list(Wrappers.<Question>lambdaQuery().in(Question::getId, unacceptedIds));
        unaccepted = count(unacceptedQuestions);
        // 未开始题目
        List<Question> unStartedQuestions = questionService.list(Wrappers.<Question>lambdaQuery().notIn(Question::getId, submittedIds));
        unStarted = count(unStartedQuestions);
        return new ResolveAnalysisChartVO().setAccepted(accepted).setUnaccepted(unaccepted).setUnStarted(unStarted);
    }

    private List<Long> count(List<Question> questions) {
        List<Long> list = new ArrayList<>();
        long easy = 0L, medium = 0L, hard = 0L;
        for (Question question : questions) {
            if ("easy".equals(question.getDifficulty())) easy++;
            else if ("medium".equals(question.getDifficulty())) medium++;
            else if ("hard".equals(question.getDifficulty())) hard++;
        }
        list.add(easy);
        list.add(medium);
        list.add(hard);
        return list;
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
