const API_URL_ENUM = {
  LIST: '/user/list/page',
  ADD: '/user/add',
  UPDATE: '/user/update',
  DELETE: '/user/delete',
}

export const UserApi = {
  /**
   * 查询用户列表
   */
  list(params?: {
    userName?: string
    userRole?: string
  } & Partial<Pagination>) {
    return useRequest.post<User, PageResult<User>>(API_URL_ENUM.LIST, params)
  },

  /**
   * 添加用户
   *
   * @returns id
   */
  add(params: Partial<User>) {
    return useRequest.post<number, Result<number>>(API_URL_ENUM.ADD, params)
  },

  /**
   * 编辑用户
   *
   * @returns boolean
   */
  update(params: Partial<User>) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.UPDATE, params)
  },

  /**
   * 删除用户
   *
   * @returns boolean
   */
  delete(params: { id?: number }) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.DELETE, params)
  },
}
