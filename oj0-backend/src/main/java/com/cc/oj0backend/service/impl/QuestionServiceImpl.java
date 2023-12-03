package com.cc.oj0backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.oj0backend.model.entity.Question;
import com.cc.oj0backend.service.QuestionService;
import com.cc.oj0backend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author cosmoscatts
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2023-12-03 19:38:53
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




