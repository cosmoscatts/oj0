package com.cc.oj0backend.gitee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.exception.ThrowUtils;
import com.cc.oj0backend.gitee.model.GiteeAccessToken;
import com.cc.oj0backend.gitee.model.GiteeAccessTokenDTO;
import com.cc.oj0backend.gitee.model.GiteeUserInfo;
import com.cc.oj0backend.gitee.service.GiteeService;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.enums.UserRoleEnum;
import com.cc.oj0backend.model.vo.LoginUserVO;
import com.cc.oj0backend.service.UserService;
import com.cc.oj0backend.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import static com.cc.oj0backend.constant.UserConstant.USER_LOGIN_STATE;

@Slf4j
@Service
public class GiteeServiceImpl implements GiteeService {
    @Resource
    private UserService userService;

    @Override
    public GiteeAccessToken getAccessToken(GiteeAccessTokenDTO giteeAccessTokenDTO) {
        Map<String, String> map = new HashMap();
        map.put("client_id", giteeAccessTokenDTO.getClient_id());
        map.put("client_secret", giteeAccessTokenDTO.getClient_secret());
        map.put("code", giteeAccessTokenDTO.getCode());
        map.put("grant_type", giteeAccessTokenDTO.getGrant_type());
        map.put("redirect_uri", giteeAccessTokenDTO.getRedirect_uri());
        HttpResponse response;
        try {
             response = HttpUtils.doPost(
                    "https://gitee.com", "/oauth/token", "post",  new HashMap<>(), map, new HashMap<>());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Gitee token 失败");
        }
        if (response == null || response.getStatusLine().getStatusCode() != 200) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Gitee token 失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略不存在的字段
        String result;
        try {
            result = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(result, GiteeAccessToken.class);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Gitee token 失败");
        }
    }

    @Override
    public GiteeUserInfo getUser(GiteeAccessToken giteeAccessToken) {
        Map<String, String> query = new HashMap<>();
        query.put("access_token", giteeAccessToken.getAccess_token());
        HttpResponse response;
        try {
            response = HttpUtils.doGet(
                    "https://gitee.com", "/api/v5/user", "get", new HashMap<>(), query);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Gitee 用户信息失败");
        }
        if (response == null || response.getStatusLine().getStatusCode() != 200) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Gitee 用户信息失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String result;
        try {
            result = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(result, GiteeUserInfo.class);
        }  catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Gitee token 失败");
        }
    }

    @Override
    public LoginUserVO userLoginByGitee(GiteeUserInfo giteeUserInfo, HttpServletRequest request) {
        String giteeId = giteeUserInfo.getId();
        // 单机锁
        synchronized (giteeId.intern()) {
            // 查询用户是否已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("giteeId", giteeId);
            User user = userService.getOne(queryWrapper);
            // 被封号，禁止登录
            if (user != null && UserRoleEnum.BAN.getValue().equals(user.getUserRole())) {
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "该用户已被封，禁止登录");
            }
            // 用户不存在则创建
            if (user == null) {
                user = new User();
                user.setGiteeId(giteeId);
                user.setUserAvatar(giteeUserInfo.getAvatar_url());
                user.setUserName(giteeUserInfo.getName());
                user.setUserProfile(giteeUserInfo.getRemark());
                boolean result = userService.save(user);
                if (!result) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
                }
            }
            // 记录用户的登录态
            request.getSession().setAttribute(USER_LOGIN_STATE, user);
            return userService.getLoginUserVO(user);
        }
    }

    @Override
    public Boolean userBindGitee(GiteeUserInfo giteeUserInfo, HttpServletRequest request) {
        String giteeId = giteeUserInfo.getId();
        // 单机锁
        synchronized (giteeId.intern()) {
            // 查询该 Gitee 账号是否已经绑定用户
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("giteeId", giteeId);
            User user = userService.getOne(queryWrapper);
            // 已绑定
            if (user != null) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "该Gitee账户已经绑定用户");
            }
            User loginUser = userService.getLoginUser(request);
            User bindUser = new User();
            bindUser.setId(loginUser.getId());
            bindUser.setGiteeId(giteeId);
            boolean result = userService.updateById(bindUser);
            ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
            return true;
        }
    }
}
