const API_URL_ENUM = {
  LIST: '/user/list/page',
  ADD: '/user/add',
  UPDATE: '/user/update',
  DELETE: '/user/delete',
  LIST_VO: '/user/list/page/vo',
  GET_VO: '/user/get/vo',
}

export const UserApi = {
  /**
   * 查询用户列表 (管理员)
   */
  list(params?: {
    userName?: string
    userRole?: string
  } & Partial<Pagination>) {
    return useRequest.post<User, PageResult<User>>(API_URL_ENUM.LIST, params)
  },

  /**
   * 查询用户
   */
  listVo() {
    return useRequest.post<User, PageResult<User>>(API_URL_ENUM.LIST_VO, { pageSize: -1 })
  },

  /**
   * 根据 id 获取用户信息
   */
  getVo(id?: string | number) {
    return useRequest.get<User, Result<User>>(API_URL_ENUM.GET_VO, { params: { id } })
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
