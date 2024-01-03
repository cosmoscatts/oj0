package com.cc.oj0backend.gitee.service;

import com.cc.oj0backend.gitee.model.GiteeAccessToken;
import com.cc.oj0backend.gitee.model.GiteeAccessTokenDTO;
import com.cc.oj0backend.gitee.model.GiteeUserInfo;
import com.cc.oj0backend.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

public interface GiteeService {
    /**
     * 获取 token
     * @param giteeAccessTokenDTO
     * @return
     */
    GiteeAccessToken getAccessToken(GiteeAccessTokenDTO giteeAccessTokenDTO);

    /**
     * 获取 Gitee 返回的用户信息
     * @param giteeAccessToken
     * @return
     */
    GiteeUserInfo getUser(GiteeAccessToken giteeAccessToken);

    /**
     * Gitee 登录
     */
    LoginUserVO userLoginByGitee(GiteeUserInfo giteeUserInfo, HttpServletRequest request);

    /**
     * 用户绑定 Gitee
     */
    Boolean userBindGitee(GiteeUserInfo giteeUserInfo, HttpServletRequest request);
}
