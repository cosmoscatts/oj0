package com.cc.oj0backend.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cc.oj0backend.common.BaseResponse;
import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.common.ResultUtils;
import com.cc.oj0backend.config.GiteeConfig;
import com.cc.oj0backend.config.GithubConfig;
import com.cc.oj0backend.exception.BusinessException;
import com.cc.oj0backend.exception.ThrowUtils;
import com.cc.oj0backend.gitee.model.GiteeAccessToken;
import com.cc.oj0backend.gitee.model.GiteeAccessTokenDTO;
import com.cc.oj0backend.gitee.model.GiteeUserInfo;
import com.cc.oj0backend.gitee.service.GiteeService;
import com.cc.oj0backend.github.model.GithubAccessToken;
import com.cc.oj0backend.github.model.GithubAccessTokenDTO;
import com.cc.oj0backend.github.model.GithubUserInfo;
import com.cc.oj0backend.github.service.GithubService;
import com.cc.oj0backend.model.dto.extraauth.BindExtraAuthRequest;
import com.cc.oj0backend.model.dto.extraauth.UnBindExtraAuthRequest;
import com.cc.oj0backend.model.entity.User;
import com.cc.oj0backend.model.vo.ExtraAuthBoundVO;
import com.cc.oj0backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 第三方登录绑定接口
 */
@RestController
@RequestMapping("/extra/auth")
@Slf4j
public class ExtraAuthBoundController {

    @Resource
    private UserService userService;

    @Resource
    private GithubService githubService;

    @Resource
    private GiteeService giteeService;

    @Resource
    private GithubConfig githubConfig;

    @Resource
    private GiteeConfig giteeConfig;

    /**
     * 获取自己的第三方登录绑定情况
     * @param request
     * @return
     */
    @GetMapping("/get/my")
    public BaseResponse<ExtraAuthBoundVO> GetMyUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        ExtraAuthBoundVO extraAuthBoundVO = new ExtraAuthBoundVO();
        extraAuthBoundVO.setGithub(user.getGithubId() != null);
        extraAuthBoundVO.setGitee(user.getGiteeId() != null);
        extraAuthBoundVO.setQq(user.getQqId() != null);
        extraAuthBoundVO.setWechat(user.getUnionId() != null);
        extraAuthBoundVO.setCanUnbound(user.getUserAccount() != null);
        return ResultUtils.success(extraAuthBoundVO);
    }

    @PostMapping("/bind/github")
    public BaseResponse<Boolean> bindMyGithub(@RequestBody BindExtraAuthRequest bindExtraAuthRequest, HttpServletRequest request) {
        String code = bindExtraAuthRequest.getCode();
        String redirectUri = bindExtraAuthRequest.getRedirectUri();
        GithubAccessToken accessToken;
        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO()
                .setClient_id(githubConfig.getClient_id())
                .setClient_secret(githubConfig.getClient_secret())
                .setCode(code)
                .setRedirect_uri(redirectUri);
        try {
            accessToken = githubService.getAccessToken(githubAccessTokenDTO);
            GithubUserInfo userInfo = githubService.getUser(accessToken);
            String githubId = userInfo.getId();
            if (StringUtils.isBlank(githubId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "绑定失败，系统错误");
            }
            return ResultUtils.success(githubService.userBindGithub(userInfo, request));
        } catch (Exception e) {
            log.error("userBindGithub error", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "绑定失败，系统错误");
        }
    }

    @PostMapping("/bind/gitee")
    public BaseResponse<Boolean> bindMyGitee(@RequestBody BindExtraAuthRequest bindExtraAuthRequest, HttpServletRequest request) {
        String code = bindExtraAuthRequest.getCode();
        GiteeAccessToken accessToken;
        GiteeAccessTokenDTO giteeAccessTokenDTO = new GiteeAccessTokenDTO()
                .setClient_id(giteeConfig.getClient_id())
                .setClient_secret(giteeConfig.getClient_secret())
                .setCode(code);
        try {
            accessToken = giteeService.getAccessToken(giteeAccessTokenDTO);
            GiteeUserInfo userInfo = giteeService.getUser(accessToken);
            String githubId = userInfo.getId();
            if (StringUtils.isBlank(githubId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "绑定失败，系统错误");
            }
            return ResultUtils.success(giteeService.userBindGitee(userInfo, request));
        } catch (Exception e) {
            log.error("userBindGithub error", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "绑定失败，系统错误");
        }
    }

    @PostMapping("/unbind/my")
    public BaseResponse<Boolean> unbindMy(@RequestBody UnBindExtraAuthRequest unBindExtraAuthRequest, HttpServletRequest request) {
        String type = unBindExtraAuthRequest.getType();
        if (type == null || !StringUtils.equalsAny(type, "github", "gitee", "qq", "wechat")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().eq("id", loginUser.getId());
        if ("github".equals(type)) {
            updateWrapper.set("githubId", null);
        } else if ("gitee".equals(type)) {
            updateWrapper.set("giteeId", null);
        } else if ("wechat".equals(type)) {
            updateWrapper.set("unionId", null);
            updateWrapper.set("mpOpenId", null);
        } else if ("qq".equals(type)) {
            updateWrapper.set("qqId", null);
        }
        boolean result = userService.update(updateWrapper);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
}
