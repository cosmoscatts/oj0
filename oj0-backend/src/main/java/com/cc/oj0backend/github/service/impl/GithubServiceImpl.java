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
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import static com.cc.oj0backend.constant.UserConstant.USER_LOGIN_STATE;

@Slf4j
@Service
public class GithubServiceImpl implements GithubService {

    @Resource
    private UserService userService;

    private OkHttpClient okHttpClient;

    public OkHttpClient getOkHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
        if (okHttpClient != null) {
            return okHttpClient;
        }
        synchronized (this) {
            if (okHttpClient != null) {
                return okHttpClient;
            }
            final X509TrustManager trustManager = new MyTrustManager();
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .hostnameVerifier((host, session) -> true)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager);
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
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略不存在的字段
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
        try {
            OkHttpClient okHttpClient = getOkHttpClient();
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            return objectMapper.readValue(result, GithubAccessToken.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取 Github token 失败");
        }
    }

    @Override
    public GithubUserInfo getUser(GithubAccessToken githubAccessToken) {
        String accessToken = githubAccessToken.getAccess_token();
        String tokenType = githubAccessToken.getToken_type();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .addHeader("Authorization",tokenType + " " + accessToken)
                .build();
        try {
            OkHttpClient okHttpClient = getOkHttpClient();
            Response response = okHttpClient.newCall(request).execute();
            String string = response.body().string();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(string, GithubUserInfo.class);
        } catch (Exception e) {
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

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
