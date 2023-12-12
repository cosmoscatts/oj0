const API_URL_ENUM = {
  LOGIN: '/user/login',
  REGISTER: '/user/register',
}

export const AuthApi = {
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
}
