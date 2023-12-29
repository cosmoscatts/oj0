const API_URL_ENUM = {
  LOGIN: '/user/login',
  LOGIN_BY_GITHUB: '/user/login/github',
  REGISTER: '/user/register',
  LOGOUT: '/user/logout',
  GET_LOGIN_USER: '/user/get/login',
  GET_MY: '/user/get/my',
  UPDATE_MY: '/user/update/my',
  UPDATE_MY_PASSWORD: '/user/update/my/password',
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
  updateMy(params: Partial<User>) {
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
}
