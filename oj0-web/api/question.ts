const API_URL_ENUM = {
  LIST: '/question/list/page',
  ADD: '/question/add',
  UPDATE: '/question/update',
  DELETE: '/question/delete',
}

export const QuestionApi = {
  /**
   * 查询完整题目列表
   */
  list(params?: {
    title?: string
    content?: string
    difficulty?: string
    tags?: string[]
  } & Partial<Pagination>) {
    return useRequest.post<QuestionDO, PageResult<QuestionDO>>(API_URL_ENUM.LIST, params)
  },

  /**
   * 添加题目
   *
   * @returns id
   */
  add(params: Partial<Question>) {
    return useRequest.post<number, Result<number>>(API_URL_ENUM.ADD, params)
  },

  /**
   * 编辑题目
   *
   * @returns boolean
   */
  update(params: Partial<Question>) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.UPDATE, params)
  },

  /**
   * 删除题目
   *
   * @returns boolean
   */
  delete(params: { id?: number }) {
    return useRequest.post<boolean, Result<boolean>>(API_URL_ENUM.DELETE, params)
  },
}
