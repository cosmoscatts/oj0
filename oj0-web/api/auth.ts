const API_URL_ENUM = {
  LOGIN: '/user/login',
  REGISTER: '/user/register',
  LOGOUT: '/user/logout',
  GET_LOGIN_USER: '/user/get/login',
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
}
