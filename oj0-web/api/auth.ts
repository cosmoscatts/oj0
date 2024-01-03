const API_URL_ENUM = {
  LOGIN: '/user/login',
  LOGIN_BY_GITHUB: '/user/login/github',
  LOGIN_BY_GITEE: '/user/login/gitee',
  REGISTER: '/user/register',
  LOGOUT: '/user/logout',
  GET_LOGIN_USER: '/user/get/login',
  GET_MY: '/user/get/my',
  UPDATE_MY: '/user/update/my',
  UPDATE_MY_PASSWORD: '/user/update/my/password',
  GET_MY_EXTRA_AUTH_BOUND: '/extra/auth/get/my',
  UNBIND_EXTRA_AUTH: '/extra/auth/unbind/my',
  BIND_EXTRA_AUTH_GITHUB: '/extra/auth/bind/github',
  BIND_EXTRA_AUTH_GITEE: '/extra/auth/bind/gitee',
  BIND_EXTRA_AUTH_QQ: '',
  BIND_EXTRA_AUTH_WECHAT: '',
}

export const AuthApi = {
  /**
   * 登录
   * @returns User
   */
  login(params: {
    userAccount?: string
    userPassword?: string
  }) {
    return useRequest.post<User, Result<User>>(API_URL_ENUM.LOGIN, params)
  },

  /**
   * Github 登录
   */
  loginByGithub(params: { code: string, redirectUri?: string }) {
    return useRequest.get<User, Result<User>>(API_URL_ENUM.LOGIN_BY_GITHUB, { params })
  },

  /**
   * Gitee 登录
   */
  loginByGitee(params: { code: string, redirectUri?: string }) {
    return useRequest.get<User, Result<User>>(API_URL_ENUM.LOGIN_BY_GITEE, { params })
  },

  /**
   * 注册
   * @returns 用户 id
   */
  register(params: {
    userAccount?: string
    userPassword?: string
    checkPassword?: string
  }) {
    return useRequest.post<number, Result<number>>(API_URL_ENUM.REGISTER, params)
  },

  /**
   * 获取登录用户
   */
  getLoginUser() {
    return useRequest.get<User, Result<User>>(API_URL_ENUM.GET_LOGIN_USER)
  },

  /**
   * 退出登录
   */
  logout() {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.LOGOUT)
  },

  /**
   * 获取个人信息
   */
  getMy() {
    return useRequest.get<User, Result<User>>(API_URL_ENUM.GET_MY)
  },

  /**
   * 更新个人信息
   */
  updateMy(params: Partial<User> & { checkPassword?: string }) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.UPDATE_MY, params)
  },

  /**
   * 更新个人密码
   */
  updateMyPassword(params: {
    userOldPassword?: string
    userNewPassword?: string
    checkNewPassword?: string
  }) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.UPDATE_MY_PASSWORD, params)
  },

  /**
   * 获取第三方绑定信息
   */
  getMyExtraAuthBound() {
    return useRequest.get<ExtraAuthBound, Result<ExtraAuthBound>>(API_URL_ENUM.GET_MY_EXTRA_AUTH_BOUND)
  },

  /**
   * 第三方绑定
   */
  bindExtraAuth(type: string, params: { code: string, redirectUri?: string }) {
    if (!['github', 'gitee', 'qq', 'wechat'].includes(type)) {
      return new Promise<Result<boolean>>((resolve) => {
        resolve({
          code: 40000,
          message: '参数错误',
          data: false,
        })
      })
    }
    if (type === 'github')
      return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.BIND_EXTRA_AUTH_GITHUB, params)

    else if (type === 'gitee')
      return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.BIND_EXTRA_AUTH_GITEE, params)

    else if (type === 'qq')
      return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.BIND_EXTRA_AUTH_GITHUB, params)

    else
      return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.BIND_EXTRA_AUTH_GITHUB, params)
  },

  /**
   * 第三方解绑
   */
  unbindExtraAuth(params: { type: string }) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.UNBIND_EXTRA_AUTH, params)
  },
}
