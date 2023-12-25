const API_URL_ENUM = {
  LIST: '/question/question_submit/list/page',
  LIST_ACCEPTED_IDS: '/question/question_submit/accepted/my',
  GET_BY_ID: '/question/question_submit/get',
  SUBMIT: '/question/question_submit/do',
}

export const QuestionSubmitApi = {
  /**
   * 查询题目提交记录
   */
  list(params?: {
    questionId?: string | number
    language?: string
    status?: number
    userId?: string | number
    sortField?: string
    sortOrder?: string
  } & Partial<Pagination>) {
    return useRequest.post<QuestionSubmit, PageResult<QuestionSubmit>>(API_URL_ENUM.LIST, params)
  },

  /**
   * 查询用户所有通过的题目 id 集合
   */
  listMyAcceptedQuestionIds() {
    return useRequest.get<number, Result<number[]>>(API_URL_ENUM.LIST_ACCEPTED_IDS)
  },

  /**
   * 根据 id 查询提交记录
   */
  getById(params: { id: string | number }) {
    return useRequest.get(API_URL_ENUM.GET_BY_ID, { params })
  },

  /**
   * 提交题目
   */
  submit(params: {
    questionId?: number | string
    language?: string
    code?: string
  }) {
    return useRequest.post<number, Result<number>>(API_URL_ENUM.SUBMIT, params)
  },
}
