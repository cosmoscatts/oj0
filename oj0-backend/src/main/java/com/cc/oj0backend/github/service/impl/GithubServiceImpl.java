package com.cc.oj0backend.github.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.github.model.GithubAccessToken;
import com.cc.oj0backend.github.model.GithubAccessTokenDTO;
import com.cc.oj0backend.github.model.GithubUserInfo;
import com.cc.oj0backend.github.service.GithubService;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.enums.UserRoleEnum;
import com.cc.oj0backend.model.vo.LoginUserVO;
import com.cc.oj0backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import okhttp3.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.cc.oj0backend.constant.UserConstant.USER_LOGIN_STATE;

@Service
public class GithubServiceImpl implements GithubService {

    @Resource
    private UserService userService;

    private OkHttpClient okHttpClient;

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) {
            return okHttpClient;
        }
        synchronized (this) {
            if (okHttpClient != null) {
                return okHttpClient;
            }
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient client = builder.connectTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true).build();
            okHttpClient = client;
            return client;
        }
    }

    @Override
    public GithubAccessToken getAccessToken(GithubAccessTokenDTO gitHubAccessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody body;
        try {
            body = RequestBody.create(mediaType,
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gitHubAccessTokenDTO));
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
        }
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .header("Accept","application/json")
                .post(body)
                .build();
        OkHttpClient okHttpClient = getOkHttpClient();
        try (Response response = okHttpClient.newCall(request).execute()) {
            String result = response.body().string();
            return objectMapper.readValue(result, GithubAccessToken.class);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取Github token 失败");
        }
    }

    @Override
    public GithubUserInfo getUser(GithubAccessToken githubAccessToken) {
        String accessToken = githubAccessToken.getAccessToken();
        String tokenType = githubAccessToken.getTokenType();

        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .addHeader("Authorization",tokenType + " " + accessToken)
                .build();
        OkHttpClient okHttpClient = getOkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String string = response.body().string();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(string, GithubUserInfo.class);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
        }
    }

    @Override
    public LoginUserVO userLoginByGithub(GithubUserInfo githubUserInfo, HttpServletRequest request) {
        String githubId = githubUserInfo.getId();
        // 单机锁
        synchronized (githubId.intern()) {
            // 查询用户是否已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("githubId", githubId);
            User user = userService.getOne(queryWrapper);
            // 被封号，禁止登录
            if (user != null && UserRoleEnum.BAN.getValue().equals(user.getUserRole())) {
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "该用户已被封，禁止登录");
            }
            // 用户不存在则创建
            if (user == null) {
                user = new User();
                user.setGithubId(githubId);
                user.setUserAvatar(githubUserInfo.getAvatar_url());
                user.setUserName(githubUserInfo.getName());
                user.setUserProfile(githubUserInfo.getBio());
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
}
