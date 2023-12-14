package com.cc.oj0backend.controller;

import com.cc.oj0backend.common.BaseResponse;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.common.ResultUtils;
import com.cc.oj0backend.constant.FileConstant;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.manager.QiNiuManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 上传文件至七牛云接口
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {
    @Resource
    private QiNiuManager qiNiuManager;

    @PostMapping("/file")
    private BaseResponse<String> upLoadImage(@RequestParam MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String filePath = qiNiuManager.uploadFile(file);
        if (filePath == null || filePath.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传文件至七牛云出错");
        }
        return ResultUtils.success(filePath);
    }
}
