const API_URL_ENUM = {
  LIST: '/question/list/page',
  ADD: '/question/add',
  UPDATE: '/question/update',
  DELETE: '/question/delete',
  LIST_VO: '/question/list/page/vo',
  GET_VO: '/question/get/vo',
  GET_RANDOM: '/question/random',
  GET_PREV: '/question/prev',
  GET_NEXT: '/question/next',
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
    createTimeMin?: string
    createTimeMax?: string
    sortField?: string
    sortOrder?: string
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

  /**
   * 查询题目（脱敏）列表
   */
  listVo(params?: {
    title?: string
    difficulty?: string
    tags?: string[]
    createTimeMin?: string
    createTimeMax?: string
    sortField?: string
    sortOrder?: string
  } & Partial<Pagination>) {
    return useRequest.post<Question, PageResult<Question>>(API_URL_ENUM.LIST_VO, params)
  },

  /**
   * 根据 id 获取题目（脱敏）
   */
  getVo(params: { id: string | number }) {
    return useRequest.get<Question, Result<Question>>(API_URL_ENUM.GET_VO, { params })
  },

  /**
   * 获取随机题号
   */
  getRandomQuestionId() {
    return useRequest.get<number, Result<number>>(API_URL_ENUM.GET_RANDOM)
  },

  /**
   * 获取上一题题号
   */
  getPrevQuestionId(params: { id: string | number }) {
    return useRequest.get<number, Result<number>>(API_URL_ENUM.GET_PREV, { params })
  },

  /**
   * 获取下一题题号
   */
  getNextQuestionId(params: { id: string | number }) {
    return useRequest.get<number, Result<number>>(API_URL_ENUM.GET_NEXT, { params })
  },
}
