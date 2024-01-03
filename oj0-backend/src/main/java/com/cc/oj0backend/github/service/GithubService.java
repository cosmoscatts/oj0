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
     * @param githubAccessTokenDTO
     * @return
     */
    GithubAccessToken getAccessToken(GithubAccessTokenDTO githubAccessTokenDTO);

    /**
     * 获取 GitHub 返回的用户信息
     * @param githubAccessToken
     * @return
     */
    GithubUserInfo getUser(GithubAccessToken githubAccessToken);

    /**
     * GitHub 登录
     */
    LoginUserVO userLoginByGithub(GithubUserInfo githubUserInfo, HttpServletRequest request);

    /**
     * 用户绑定 github
     */
    Boolean userBindGithub(GithubUserInfo githubUserInfo, HttpServletRequest request);
}
