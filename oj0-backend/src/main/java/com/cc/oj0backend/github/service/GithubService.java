package com.cc.oj0backend.github.service;

import com.cc.oj0backend.github.model.GithubAccessToken;
import com.cc.oj0backend.github.model.GithubAccessTokenDTO;
import com.cc.oj0backend.github.model.GithubUserInfo;
import com.cc.oj0backend.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * GitHub 登录服务
 */
public interface GithubService {
    /**
     * 获取 token
     * @param gitHubAccessTokenDTO
     * @return
     */
    GithubAccessToken getAccessToken(GithubAccessTokenDTO gitHubAccessTokenDTO);

    /**
     * 获取 GitHub 返回的用户信息
     * @param gitHubAccessToken
     * @return
     */
    GithubUserInfo getUser(GithubAccessToken gitHubAccessToken);

    /**
     * GitHub 登录
     */
    LoginUserVO userLoginByGithub(GithubUserInfo gitHubUserInfo, HttpServletRequest request);
}
