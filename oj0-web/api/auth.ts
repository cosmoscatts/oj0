const API_URL_ENUM = {
  LOGIN: '/user/login',
  REGISTER: '/user/register',
  GET_LOGIN_USER: '/user/get/login',
}

export const AuthApi = {
  /**
   * 登录
   * @returns User
   */
  async login(params: {
    userAccount?: string
    userPassword?: string
  }) {
    return await useRequest.post<User, Result<User>>(API_URL_ENUM.LOGIN, params)
  },

  /**
   * 注册
   * @returns 用户 id
   */
  async register(params: {
    userAccount?: string
    userPassword?: string
    checkPassword?: string
  }) {
    return await useRequest.post<number, Result<number>>(API_URL_ENUM.REGISTER, params)
  },

  /**
   * 获取登录用户
   */
  async getLoginUser() {
    return await useRequest.get<User, Result<User>>(API_URL_ENUM.GET_LOGIN_USER)
  },
}
