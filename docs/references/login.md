---
outline: deep
---

# 第三方登录

<br>

> 大部分第三方登录都大同小异，这里以 `Gitee` 为例

## 大致流程

![流程](/public/refers/login/img1.png/)

- 通过申请的应用 `ID–Client ID`、回调地址等向 `Gitee` 服务器发起授权的请求

- `Gitee` 认证服务器通过回调地址 `redirect_uri` 将 `用户授权码 code` 传递回来（传递到回调地址）

- 通过 `用户授权码 code` 及 `应用 ID` 等信息，再去 `Gitee` 服务器中获取用户的访问令牌 `Access Token`

- 获取 `Access Token` 之后，根据这个 `token` 再去 `Gitee` 服务器中获取用户的 ID、name、email 等信息

## 代码实现

### 定义实体类

::: details 实体类
::: code-group
```java [GiteeAccessToken.java]
@Data
public class GiteeAccessToken {
    private String access_token;
}
```

```java [GiteeAccessTokenDTO.java]
@Data
@Accessors(chain = true)
public class GiteeAccessTokenDTO {
    private String grant_type = "authorization_code";
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri = "https://xxx/gitee/";
}
```

```java [GiteeUserInfo.java]
@Data
@Accessors(chain = true)
public class GiteeUserInfo {
    private String id;
    private String login;
    private String name;
    private String avatar_url;
    private String remark;
}
```
:::


### Gitee 登录服务

::: details 登录服务
::: code-group
```java [GiteeService.java]
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
```

```java [GiteeServiceImpl.java]
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
```
:::


### api 接口

```java
/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private GiteeService giteeService;

    @Resource
    private GiteeConfig giteeConfig;
    
   /**
     * 用户登录（Gitee）
     */
    @GetMapping("/login/gitee")
    public BaseResponse<LoginUserVO> userLoginByGitee(HttpServletRequest request, @RequestParam("code") String code) {
        GiteeAccessToken accessToken;
        GiteeAccessTokenDTO giteeAccessTokenDTO = new GiteeAccessTokenDTO()
                .setClient_id(giteeConfig.getClient_id())
                .setClient_secret(giteeConfig.getClient_secret())
                .setCode(code);
        try {
            accessToken = giteeService.getAccessToken(giteeAccessTokenDTO);
            GiteeUserInfo userInfo = giteeService.getUser(accessToken);
            String giteeId = userInfo.getId();
            if (StringUtils.isBlank(giteeId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
            }
            return ResultUtils.success(giteeService.userLoginByGitee(userInfo, request));
        } catch (Exception e) {
            log.error("userLoginByGitee error", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
        }
    }
}
```
